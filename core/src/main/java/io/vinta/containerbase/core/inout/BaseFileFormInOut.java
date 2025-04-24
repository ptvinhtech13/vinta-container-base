package io.vinta.containerbase.core.inout;

import io.vinta.containerbase.core.containers.ContainerCommandService;
import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseFileFormInOut {

	@Autowired
	protected ContainerQueryService containerQueryService;

	@Autowired
	protected ContainerCommandService containerCommandService;

	@Autowired
	protected ImportJobCommandService importCommandService;

	@Autowired
	protected ExportJobCommandService exportCommandService;

	@Autowired
	protected FileFormManagerService fileFormManagerService;

	@SneakyThrows
	protected void createFileIfNotExists(Path path) {
		if (path.getParent() != null && Files.notExists(path.getParent())) {
			Files.createDirectories(path.getParent());
		}

		if (Files.notExists(path)) {
			Files.createFile(path);
		}
	}

}
