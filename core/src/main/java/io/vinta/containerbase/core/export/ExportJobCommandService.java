package io.vinta.containerbase.core.export;

import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;

public interface ExportJobCommandService {
	ExportJob createExportJob(CreateExportJobCommand command);

	ExportJob updateExportJob(ExportJob exportJob);
}
