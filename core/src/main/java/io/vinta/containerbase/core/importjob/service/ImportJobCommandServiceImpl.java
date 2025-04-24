package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.mapper.ImportJobMapper;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportJobCommandServiceImpl implements ImportJobCommandService {
	private final ImportJobRepository repository;

	@Override
	public ImportJob createImportJob(CreateImportJobCommand command) {
		log.info("Creating import job: {}", command.toString());
		return repository.save(ImportJobMapper.INSTANCE.toNewEntity(command));
	}

	@Override
	public ImportJob updateImportJob(ImportJob importJob) {
		return repository.save(importJob);
	}
}
