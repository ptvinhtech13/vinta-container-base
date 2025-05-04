package io.vinta.containerbase.tests.commons.supporter;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInitializationIntegrationSupporter {

	private final UserCommandService userCommandService;
	private final RoleQueryService roleQueryService;

	public User createUser(TenantId tenantId, DefaultSystemRole userRole, CreateUserCommand command) {
		command = command.withUserAccess(CreateUserAccessCommand.builder()
				.accessType(UserAccessType.BASIC_AUTH)
				.accessData(UserAccessBasicAuthData.builder()
						.password("0000")
						.build())
				.build())
				.withUserRole(CreateUserRoleCommand.builder()
						.roleId(roleQueryService.queryRoles(RolePaginationQuery.builder()
								.filter(FilterRoleQuery.builder()
										.byTenantId(tenantId)
										.byRoleKeys(Set.of(userRole.getRoleKey()))
										.build())
								.page(0)
								.size(1)
								.sortDirection("ASC")
								.sortFields(List.of("id"))
								.build())
								.getContent()
								.getFirst()
								.getId())
						.tenantId(tenantId)
						.build());

		return userCommandService.createUser(command);
	}
}
