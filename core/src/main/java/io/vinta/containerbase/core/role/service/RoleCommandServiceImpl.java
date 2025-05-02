package io.vinta.containerbase.core.role.service;

import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleRepository;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.mapper.RoleMapper;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCommandServiceImpl implements RoleCommandService {
	private final RoleRepository repository;

	@Override
	public Role createRole(CreateRoleCommand command) {
		return repository.save(RoleMapper.INSTANCE.toCreate(command));

	}

	@Override
	public Role updateRole(UpdateRoleCommand command) {
		final var role = repository.findRoleById(command.getRoleId())
				.orElseThrow(() -> new NotFoundException("Role not found"));
		return repository.save(RoleMapper.INSTANCE.toUpdate(role, command));
	}

}
