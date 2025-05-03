package io.vinta.containerbase.app.users;

import io.vinta.containerbase.common.constants.TenantConstants;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import java.util.List;
import java.util.Set;
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

		if (userQueryService.findSingleUser(FilterUserQuery.builder()
				.byEmail(superAdminConfigProperties.getEmail())
				.build())
				.isPresent()) {
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
					.build());
		}

		userCommandService.createUser(CreateUserCommand.builder()
				.userType(UserType.SYSTEM_ADMIN)
				.email(superAdminConfigProperties.getEmail())
				.fullName(superAdminConfigProperties.getFullName())
				.userStatus(UserStatus.ACTIVE)
				.userAccess(CreateUserAccessCommand.builder()
						.accessType(UserAccessType.BASIC_AUTH_PASSWORD)
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
