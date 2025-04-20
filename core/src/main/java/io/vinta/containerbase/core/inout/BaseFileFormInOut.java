package io.vinta.containerbase.core.inout;

import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseFileFormInOut {

	@Autowired
	protected ContainerQueryService containerQueryService;

	@Autowired
	protected ExportJobCommandService exportCommandService;

}
