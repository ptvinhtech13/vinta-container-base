package io.vinta.containerbase.core.importjob.request;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.core.importjob.entities.FileDataSource;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateImportJobCommand {
	private final ImportJobId id;
	private final Set<FileDataSource> sources;
}