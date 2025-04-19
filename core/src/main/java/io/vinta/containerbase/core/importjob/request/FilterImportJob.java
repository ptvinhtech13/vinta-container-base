package io.vinta.containerbase.core.importjob.request;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilterImportJob {
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
}
