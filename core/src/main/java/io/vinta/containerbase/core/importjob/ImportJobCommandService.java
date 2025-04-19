package io.vinta.containerbase.core.importjob;

import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;

public interface ImportJobCommandService {
	ImportJob createImportJob(CreateImportJobCommand command);
}
