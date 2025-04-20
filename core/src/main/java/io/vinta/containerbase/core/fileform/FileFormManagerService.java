package io.vinta.containerbase.core.fileform;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.core.inout.FileFormExporter;
import java.util.List;
import java.util.Optional;

public interface FileFormManagerService {
	void init(List<FileForm> fileForms);

	Optional<FileForm> getFileForm(FileFormId id);

	Optional<FileFormExporter> getExporter(FileFormId id);
}
