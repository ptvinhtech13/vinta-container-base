package io.vinta.containerbase.core.importrecord;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.core.importrecord.request.FindImportRecordQuery;

public interface ImportRecordQueryService {

	Paging<ImportRecord> queryImportRecords(FindImportRecordQuery query);
}
