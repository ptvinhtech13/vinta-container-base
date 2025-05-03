package io.vinta.containerbase.core.role;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;

public interface RoleQueryService {
	Role getRole(TenantId tenantId, RoleId roleId);

	Paging<Role> queryRoles(RolePaginationQuery pagingQuery);

	Role getRoleKey(TenantId tenantId, String roleKey);
}
