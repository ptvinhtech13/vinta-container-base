package io.vinta.containerbase.core.containers;

import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.CreateContainerCommand;
import java.util.List;

public interface ContainerCommandService {
	List<Container> createContainers(List<CreateContainerCommand> commands);
}
