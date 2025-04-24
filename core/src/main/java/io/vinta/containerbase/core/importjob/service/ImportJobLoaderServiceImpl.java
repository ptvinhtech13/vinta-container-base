package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.common.exceptions.ContainerBaseException;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.ImportJobLoaderService;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importrecord.ImportRecordCommandService;
import io.vinta.containerbase.core.inout.FileFormImporter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportJobLoaderServiceImpl implements ImportJobLoaderService {

	private final List<FileFormImporter> fileFormLoaders;
	private final ImportJobCommandService jobCommandService;
	private final ImportRecordCommandService recordCommandService;

	@Override
	public void loadRecords(ImportJob job) {
		try {
			job = jobCommandService.updateImportJob(job.withStatus(ImportJobStatus.LOADING));

			// clean previous records before starting all new
			recordCommandService.deleteRecords(job.getId());

			final var fileFormId = job.getFileFormId();
			job = fileFormLoaders.stream()
					.filter(loader -> loader.hasSupport(fileFormId))
					.findFirst()
					.orElseThrow(() -> new ContainerBaseException("No loader found for form: " + fileFormId))
					.load(job, recordCommandService::upsertRecords);

			jobCommandService.updateImportJob(job.withStatus(ImportJobStatus.VALIDATED));
		} catch (Exception e) {
			log.error("Failed to load records for job: {}", job.getId(), e);
			jobCommandService.updateImportJob(job.withStatus(ImportJobStatus.ERROR)
					.withRemark(e.getMessage()));
		}

	}
}
