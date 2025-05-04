package io.vinta.containerbase.app.users;

import io.vinta.containerbase.common.constants.TenantConstants;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.security.permissions.ApiPermissionKey;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.common.security.permissions.FeatureNodeType;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperUserEventService {
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;
	private final RoleQueryService roleQueryService;
	private final RoleCommandService roleCommandService;
	private final SuperAdminConfigProperties superAdminConfigProperties;

	@EventListener(ApplicationReadyEvent.class)
	public void handleUserCreatedEvent() {
		final var tenantId = MapstructCommonDomainMapper.INSTANCE.longToTenantId(TenantConstants.ADMIN_TENANT_ID);

		final var existingOptional = userQueryService.findSingleUser(FilterUserQuery.builder()
				.byEmail(superAdminConfigProperties.getEmail())
				.build());

		if (existingOptional.isPresent()) {

			final var userRole = existingOptional.get()
					.getUserRoles()
					.stream()
					.findFirst()
					.get();

			final var role = roleQueryService.getRole(tenantId, userRole.getRoleId());
			roleCommandService.updateRole(UpdateRoleCommand.builder()
					.tenantId(tenantId)
					.roleId(role.getId())
					.title(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleTitle())
					.description(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleTitle())
					.featureNodeIds(Arrays.stream(ApiPermissionKey.values())
							.filter(it -> FeatureNodeType.API.equals(it.getNodeType()))
							.map(ApiPermissionKey::getId)
							.collect(Collectors.toSet()))
					.build());

			return;
		}

		if (roleQueryService.queryRoles(RolePaginationQuery.builder()
				.filter(FilterRoleQuery.builder()
						.byTenantId(tenantId)
						.byRoleKeys(Set.of(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey()))
						.build())
				.page(0)
				.size(1)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build())
				.getContent()
				.isEmpty()) {
			roleCommandService.createRole(CreateRoleCommand.builder()
					.tenantId(tenantId)
					.title(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleTitle())
					.description(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleTitle())
					.roleKey(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey())
					.featureNodeIds(Arrays.stream(ApiPermissionKey.values())
							.filter(it -> FeatureNodeType.API.equals(it.getNodeType()))
							.map(ApiPermissionKey::getId)
							.collect(Collectors.toSet()))
					.build());
		}

		userCommandService.createUser(CreateUserCommand.builder()
				.userType(UserType.SYSTEM_ADMIN)
				.email(superAdminConfigProperties.getEmail())
				.fullName(superAdminConfigProperties.getFullName())
				.userStatus(UserStatus.ACTIVE)
				.userAccess(CreateUserAccessCommand.builder()
						.accessType(UserAccessType.BASIC_AUTH)
						.accessData(UserAccessBasicAuthData.builder()
								.password(superAdminConfigProperties.getPassword())
								.build())
						.build())
				.userRole(CreateUserRoleCommand.builder()
						.tenantId(tenantId)
						.roleId(roleQueryService.queryRoles(RolePaginationQuery.builder()
								.filter(FilterRoleQuery.builder()
										.byTenantId(tenantId)
										.byRoleKeys(Set.of(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey()))
										.build())
								.page(0)
								.size(1)
								.sortDirection("ASC")
								.sortFields(List.of("id"))
								.build())
								.getContent()
								.getFirst()
								.getId())
						.build())

				.build());
	}
}
