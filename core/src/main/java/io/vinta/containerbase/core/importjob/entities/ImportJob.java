package io.vinta.containerbase.core.importjob.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
@ToString(of = { "id", "status", "fileFormId", "uploadedFilePath", "remark", "createdAt", "updatedAt" })
public class ImportJob extends BaseEntity<ImportJobId> {
	private final ImportJobId id;
	private final ImportJobStatus status;
	private final FileFormId fileFormId;
	private final String uploadedFilePath;
	private final FileFormSchema actualSchema;
	private final ImportJobTrackingMetrics metrics;
	private final String consolidatedErrorMessages;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}