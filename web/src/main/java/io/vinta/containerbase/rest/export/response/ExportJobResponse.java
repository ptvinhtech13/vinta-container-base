package io.vinta.containerbase.rest.export.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExportJobResponse {
	private final String id;
	private final FileFormResponse exportForm;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
