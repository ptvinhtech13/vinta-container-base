package io.vinta.containerbase.core.importrecord;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.core.importrecord.request.FindImportRecordQuery;
import java.util.List;

public interface ImportRecordRepository {
	void deleteAllByImportJobId(ImportJobId jobId);

	List<ImportRecord> saveAll(List<ImportRecord> records);

	Paging<ImportRecord> queryImportRecords(FindImportRecordQuery query);
}
