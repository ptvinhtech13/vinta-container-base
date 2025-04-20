package io.vinta.containerbase.core.export;

import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;
import java.util.Optional;

public interface ExportJobRepository {
	Paging<ExportJob> queryExportJobs(FindExportJobQuery request);

	ExportJob save(ExportJob job);

	Optional<ExportJob> findOneByExportJobId(ExportJobId exportJobId);

}
