package io.vinta.containerbase.core.importjob;

import io.vinta.containerbase.core.importjob.entities.ImportJob;

public interface ImportJobProcessorService {
	void processImport(ImportJob job);
}
