package io.vinta.containerbase.core.export.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class ExportJob extends BaseEntity<ExportJobId> {
	private final ExportJobId id;
	private final FileForm exportForm;
	private final String remark;
	private final ExportJobStatus status;
	private final Long totalContainer;
	private final Integer totalPage;
	private final Long totalExportedContainer;
	private final Integer lastExportedPage;
	private final FilterContainer filterContainer;
	private final String fileOutputPath;
	private final Instant createdAt;
	private final Instant updatedAt;
}