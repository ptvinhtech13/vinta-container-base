package io.vinta.containerbase.rest.importjob.response;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.importjob.entities.ImportJobTrackingMetrics;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ImportJobResponse {
	private final String id;
	private final ImportJobStatus status;
	private final String fileFormId;
	private final String uploadedFilePath;
	private final String consolidatedErrorMessages;
	private final ImportJobTrackingMetrics metrics;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
