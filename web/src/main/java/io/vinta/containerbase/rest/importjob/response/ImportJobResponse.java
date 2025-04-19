package io.vinta.containerbase.rest.importjob.response;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.importjob.entities.FileDataSource;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ImportJobResponse {
	private final String id;
	private final ImportJobStatus status;
	private final List<FileDataSource> sources;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
