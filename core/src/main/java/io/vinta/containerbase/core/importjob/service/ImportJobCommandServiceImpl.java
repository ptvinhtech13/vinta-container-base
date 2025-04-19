package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.mapper.ImportJobMapper;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportJobCommandServiceImpl implements ImportJobCommandService {
	private final ImportJobRepository repository;

	@Override
	public ImportJob createImportJob(CreateImportJobCommand command) {
		return repository.save(ImportJobMapper.INSTANCE.toEntity(command));
	}
}
