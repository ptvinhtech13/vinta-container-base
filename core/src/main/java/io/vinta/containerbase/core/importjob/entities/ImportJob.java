package io.vinta.containerbase.core.importjob.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.enums.ImportJobStatus;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class ImportJob extends BaseEntity<ImportJobId> {
	private final ImportJobId id;
	private final ImportJobStatus status;
	private final List<FileDataSource> sources;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}