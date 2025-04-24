package io.vinta.containerbase.web.importjob.views;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.importjob.entities.ImportJobTrackingMetrics;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ImportJobView {
	private final ImportJobId id;
	private final ImportJobStatus status;
	private final String fileFormId;
	private final String uploadedFilePath;
	private final ImportJobTrackingMetrics metrics;
	private final String consolidatedErrorMessages;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
