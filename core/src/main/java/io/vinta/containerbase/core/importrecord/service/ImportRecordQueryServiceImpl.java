package io.vinta.containerbase.core.importrecord.service;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.importrecord.ImportRecordQueryService;
import io.vinta.containerbase.core.importrecord.ImportRecordRepository;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.core.importrecord.request.FindImportRecordQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportRecordQueryServiceImpl implements ImportRecordQueryService {
	private final ImportRecordRepository repository;

	@Override
	public Paging<ImportRecord> queryImportRecords(FindImportRecordQuery query) {
		return repository.queryImportRecords(query);
	}
}
