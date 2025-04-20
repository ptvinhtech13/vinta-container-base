package io.vinta.containerbase.core.inout;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.export.entities.ExportJob;

public interface FileFormExporter {
	boolean hasSupport(FileFormId formId);

	ExportJob export(ExportJob job);
}
