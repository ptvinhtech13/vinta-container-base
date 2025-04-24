package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.common.enums.ImportRecordStatus;
import io.vinta.containerbase.common.exceptions.ContainerBaseException;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.ImportJobProcessorService;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importrecord.ImportRecordCommandService;
import io.vinta.containerbase.core.importrecord.ImportRecordQueryService;
import io.vinta.containerbase.core.importrecord.request.FilterImportRecord;
import io.vinta.containerbase.core.importrecord.request.FindImportRecordQuery;
import io.vinta.containerbase.core.inout.FileFormImporter;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportJobProcessorServiceImpl implements ImportJobProcessorService {

	private final List<FileFormImporter> fileFormImporters;
	private final ImportJobCommandService jobCommandService;
	private final ImportRecordQueryService recordQueryService;
	private final ImportRecordCommandService recordCommandService;

	@Override
	public void processImport(ImportJob job) {
		try {
			job = jobCommandService.updateImportJob(job.withStatus(ImportJobStatus.IMPORTING));

			final var fileFormId = job.getFileFormId();
			final var importer = fileFormImporters.stream()
					.filter(loader -> loader.hasSupport(fileFormId))
					.findFirst()
					.orElseThrow(() -> new ContainerBaseException("No processor found for form: " + fileFormId));

			final var records = recordQueryService.queryImportRecords(FindImportRecordQuery.builder()
					.filter(FilterImportRecord.builder()
							.byImportJobId(job.getId())
							.byStatuses(Set.of(ImportRecordStatus.VALIDATED))
							.build())
					.page(0)
					.size(300)
					.sortDirection("ASC")
					.sortFields(List.of("id"))
					.build());

			final var updatingRecords = recordCommandService.upsertRecords(importer.processRecords(records
					.getContent()));

			if (updatingRecords.isEmpty()) {
				return;
			}

			final var totalProcessedRecords = records.getContent()
					.size() + job.getMetrics()
							.getTotalProcessedRecords();

			final var status = totalProcessedRecords == job.getMetrics()
					.getTotalRecords() ? ImportJobStatus.SUCCEEDED : ImportJobStatus.IMPORTING;

			jobCommandService.updateImportJob(job.withStatus(status)
					.withMetrics(job.getMetrics()
							.withTotalProcessedRecords(totalProcessedRecords)));
		} catch (Exception e) {
			log.error("Failed to load records for job: {}", job.getId(), e);
			jobCommandService.updateImportJob(job.withStatus(ImportJobStatus.ERROR)
					.withRemark(e.getMessage()));
		}

	}
}
