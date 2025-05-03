package io.vinta.containerbase.core.role;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;

public interface RoleCommandService {
	Role createRole(CreateRoleCommand command);

	Role updateRole(UpdateRoleCommand command);

	void deleteRole(TenantId tenantId, RoleId roleId);
}
