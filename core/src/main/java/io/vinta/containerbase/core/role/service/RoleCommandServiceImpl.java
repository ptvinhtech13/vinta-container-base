package io.vinta.containerbase.core.role.service;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.security.permissions.ApiPermissionKey;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.common.security.permissions.FeatureNodeType;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleRepository;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.mapper.RoleMapper;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;
import io.vinta.containerbase.core.tenant.events.TenantCreatedEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCommandServiceImpl implements RoleCommandService {
	private final RoleRepository repository;
	private final FeatureNodeQueryService featureNodeQueryService;

	@Override
	public Role createRole(CreateRoleCommand command) {
		return repository.save(RoleMapper.INSTANCE.toCreate(command));
	}

	@Override
	public Role updateRole(UpdateRoleCommand command) {
		final var role = repository.findRoleById(command.getTenantId(), command.getRoleId())
				.orElseThrow(() -> new NotFoundException("Role not found"));
		return repository.save(RoleMapper.INSTANCE.toUpdate(role, command));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(TenantId tenantId, RoleId roleId) {
		repository.deleteRole(tenantId, roleId);
	}

	@EventListener
	public void handleUserCreatedEvent(TenantCreatedEvent event) {
		Arrays.stream(DefaultSystemRole.values())
				.filter(role -> !role.isDefaultSystemTenant())
				.map(role -> CreateRoleCommand.builder()
						.tenantId(event.getTenant()
								.getId())
						.title(role.getRoleTitle())
						.description("This role was auto-created by the system")
						.roleKey(role.getRoleKey())
						.featureNodeIds(Stream.concat(role.getAllowedPermissions()
								.stream()
								.filter(it -> FeatureNodeType.API.equals(it.getNodeType()))
								.map(ApiPermissionKey::getId), role.getAllowedPermissions()
										.stream()
										.filter(it -> FeatureNodeType.MODULE.equals(it.getNodeType()))
										.map(it -> featureNodeQueryService.getChildrenNodeByParentPath(it
												.getNodePath()))
										.flatMap(List::stream)
										.map(ApiPermissionKey::getId))
								.collect(Collectors.toSet()))
						.build())
				.forEach(this::createRole);

	}

}
