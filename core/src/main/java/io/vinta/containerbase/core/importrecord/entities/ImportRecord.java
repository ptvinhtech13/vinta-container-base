package io.vinta.containerbase.core.importrecord.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.baseid.ImportRecordId;
import io.vinta.containerbase.common.enums.ImportRecordStatus;
import io.vinta.containerbase.common.enums.ImportRecordType;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class ImportRecord extends BaseEntity<ImportRecordId> {
	private final ImportRecordId id;
	private final ImportJobId importJobId;
	private final long recordIndex;
	private final ImportRecordType recordType;
	private final ImportRecordStatus recordStatus;
	private final String errorMessage;
	private final String stacktrace;
	private final Map<String, String> data;
	private final String rawData;
}