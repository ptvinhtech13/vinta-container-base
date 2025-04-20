package io.vinta.containerbase.web.export.views;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.web.fileform.views.FileFormView;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExportJobView {
	private final Long id;
	private final FileFormView exportForm;
	private final ExportJobStatus status;
	private final Long totalContainer;
	private final String fileOutputPath;
	private final Long totalExportedContainer;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;
}
