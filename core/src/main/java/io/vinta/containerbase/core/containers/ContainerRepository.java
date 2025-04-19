package io.vinta.containerbase.core.containers;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import java.util.List;

public interface ContainerRepository {
	Paging<Container> queryContainers(FindContainerQuery query);

	List<Container> saveAll(List<Container> containers);

}
