package io.vinta.containerbase.core.role;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import java.util.Optional;

public interface RoleRepository {
	Role save(Role role);

	Optional<Role> findRoleById(TenantId tenantId, RoleId roleId);

	Paging<Role> queryRoles(RolePaginationQuery pagingQuery);

	Optional<Role> findRoleByKey(TenantId tenantId, String roleKey);

	void deleteRole(TenantId tenantId, RoleId roleId);
}
