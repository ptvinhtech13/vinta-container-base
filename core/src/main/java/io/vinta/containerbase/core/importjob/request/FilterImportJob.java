package io.vinta.containerbase.core.importjob.request;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilterImportJob {
	private Set<ImportJobStatus> byStatuses;
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
}
