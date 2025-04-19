package io.vinta.containerbase.web.importz.views;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.enums.ImportJobStatus;
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
	private final String sources;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
