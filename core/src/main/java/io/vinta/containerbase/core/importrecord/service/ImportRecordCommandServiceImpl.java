package io.vinta.containerbase.core.importrecord.service;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.core.importrecord.ImportRecordCommandService;
import io.vinta.containerbase.core.importrecord.ImportRecordRepository;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportRecordCommandServiceImpl implements ImportRecordCommandService {
	private final ImportRecordRepository repository;

	@Override
	public List<ImportRecord> upsertRecords(List<ImportRecord> records) {
		return repository.saveAll(records);
	}

	@Override
	public void deleteRecords(ImportJobId jobId) {
		repository.deleteAllByImportJobId(jobId);
	}
}
