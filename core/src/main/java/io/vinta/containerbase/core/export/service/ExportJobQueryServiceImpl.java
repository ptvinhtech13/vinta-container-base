package io.vinta.containerbase.core.export.service;

import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.export.ExportJobQueryService;
import io.vinta.containerbase.core.export.ExportJobRepository;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportJobQueryServiceImpl implements ExportJobQueryService {
	private final ExportJobRepository repository;

	@Override
	public Paging<ExportJob> queryExportJobs(FindExportJobQuery request) {
		return repository.queryExportJobs(request);

	}

	@Override
	public Optional<ExportJob> getExportJob(ExportJobId exportJobId) {
		return repository.findOneByExportJobId(exportJobId);

	}
}
