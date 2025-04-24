package io.vinta.containerbase.core.inout;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import java.util.List;
import java.util.function.Consumer;

public interface FileFormImporter {
	boolean hasSupport(FileFormId fileFormId);

	ImportJob load(ImportJob job, Consumer<List<ImportRecord>> batchingRecordConsumer);

	List<ImportRecord> processRecords(List<ImportRecord> content);

}
