package io.vinta.containerbase.core.importjob.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@ToString
public class ImportJobTrackingMetrics {
	private long totalRecords;;
	private long totalRecordsError;
	private long totalProcessedRecords;
}
