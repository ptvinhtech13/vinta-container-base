package io.vinta.containerbase.core.inout.models;

import io.vinta.containerbase.core.importjob.entities.ImportJob;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@With
public class ProcessedCsvResult {
	private final ImportJob job;
	private final long totalRecords;
	private final long totalErrorRecords;
	private final String consolidatedErrorMessages;
}
