package io.vinta.containerbase.core.importrecord;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import java.util.List;

public interface ImportRecordCommandService {
	List<ImportRecord> upsertRecords(List<ImportRecord> records);

	void deleteRecords(ImportJobId records);
}
