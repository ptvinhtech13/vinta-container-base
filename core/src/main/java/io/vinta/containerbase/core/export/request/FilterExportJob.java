package io.vinta.containerbase.core.export.request;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilterExportJob {
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
	private Set<ExportJobStatus> byStatuses;
}
