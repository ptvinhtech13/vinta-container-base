package io.vinta.containerbase.core.containers.service;

import io.vinta.containerbase.core.containers.ContainerCommandService;
import io.vinta.containerbase.core.containers.ContainerRepository;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.mapper.ContainerMapper;
import io.vinta.containerbase.core.containers.request.CreateContainerCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerCommandServiceImpl implements ContainerCommandService {
	private final ContainerRepository repository;

	@Override
	public List<Container> createContainers(List<CreateContainerCommand> commands) {
		return repository.saveAll(commands.stream()
				.map(ContainerMapper.INSTANCE::toCreateContainer)
				.toList());

	}
}
