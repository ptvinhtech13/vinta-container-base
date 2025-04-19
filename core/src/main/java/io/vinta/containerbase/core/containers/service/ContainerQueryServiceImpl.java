package io.vinta.containerbase.core.containers.service;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.containers.ContainerRepository;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerQueryServiceImpl implements ContainerQueryService {

	private final ContainerRepository repository;

	@Override
	public Paging<Container> queryContainers(FindContainerQuery query) {
		return repository.queryContainers(query);
	}

}
