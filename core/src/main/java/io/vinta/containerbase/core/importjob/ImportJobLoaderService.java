package io.vinta.containerbase.core.importjob;

import io.vinta.containerbase.core.importjob.entities.ImportJob;

public interface ImportJobLoaderService {
	void loadRecords(ImportJob job);
}
