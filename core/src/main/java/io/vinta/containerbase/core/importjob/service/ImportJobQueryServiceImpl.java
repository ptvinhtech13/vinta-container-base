package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.importjob.ImportJobQueryService;
import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportJobQueryServiceImpl implements ImportJobQueryService {
	private final ImportJobRepository repository;

	@Override
	public Paging<ImportJob> queryImportJobs(FindImportJobQuery query) {
		return repository.queryImportJobs(query);

	}
}
