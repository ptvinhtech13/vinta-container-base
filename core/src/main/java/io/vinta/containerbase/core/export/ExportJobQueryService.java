package io.vinta.containerbase.core.export;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;

public interface ExportJobQueryService {
	Paging<ExportJob> queryExportJobs(FindExportJobQuery request);

}
