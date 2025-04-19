package io.vinta.containerbase.core.importjob;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;

public interface ImportJobRepository {
	ImportJob save(ImportJob job);

	Paging<ImportJob> queryImportJobs(FindImportJobQuery query);
}
