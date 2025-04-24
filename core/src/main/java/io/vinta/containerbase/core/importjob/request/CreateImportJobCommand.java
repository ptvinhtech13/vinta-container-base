package io.vinta.containerbase.core.importjob.request;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateImportJobCommand {
	private final ImportJobId id;
	private final String uploadedFilePath;
	private final FileFormId fileFormId;
}