package io.vinta.containerbase.tests.commons.supporter;

import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleInitializationIntegrationSupporter {

	private final RoleCommandService roleCommandService;

	public Role createRole(CreateRoleCommand command) {
		return roleCommandService.createRole(command);
	}
}
