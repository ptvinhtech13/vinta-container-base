package io.vinta.containerbase.core.fileform.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.enums.FileFormAction;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class FileForm extends BaseEntity<FileFormId> {
	private final FileFormId id;
	private final FileFormSchema schema;
	private final Set<FileFormAction> actions;
}