package io.vinta.containerbase.rest.export.request;

import io.vinta.containerbase.core.containers.request.FilterContainer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateExportJobRequest {
	@NotEmpty
	private final String exportFormId;
	private final String remark;
	private final FilterContainer filterContainer;
}
