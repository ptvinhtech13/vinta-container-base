package io.vinta.containerbase.core.export.request;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateExportJobCommand {
	private final FileFormId exportedFormId;
	private final String remark;
	private final FilterContainer filterContainer;
}