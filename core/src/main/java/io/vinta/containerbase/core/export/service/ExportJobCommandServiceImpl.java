package io.vinta.containerbase.core.export.service;

import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.export.ExportJobRepository;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.mapper.ExportJobMapper;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportJobCommandServiceImpl implements ExportJobCommandService {
	private final ExportJobRepository repository;
	private final FileFormManagerService fileFormManagerService;

	@Override
	public ExportJob createExportJob(CreateExportJobCommand command) {
		return repository.save(ExportJobMapper.INSTANCE.toNewExportJob(command, fileFormManagerService.getFileForm(
				command.getExportedFormId())
				.orElseThrow()));

	}

	@Override
	public ExportJob updateExportJob(ExportJob exportJob) {
		return repository.save(exportJob);
	}
}
