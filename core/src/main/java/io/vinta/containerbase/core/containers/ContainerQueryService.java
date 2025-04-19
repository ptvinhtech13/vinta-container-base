package io.vinta.containerbase.core.containers;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;

public interface ContainerQueryService {
	Paging<Container> queryContainers(FindContainerQuery query);
}
