package io.vinta.containerbase.core.importrecord.request;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.enums.ImportRecordStatus;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilterImportRecord {
	private Set<ImportRecordStatus> byStatuses;
	private ImportJobId byImportJobId;
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
}
