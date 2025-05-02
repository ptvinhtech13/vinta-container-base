package io.vinta.containerbase.core.role;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import java.util.Optional;

public interface RoleRepository {
	Role save(Role role);

	Optional<Role> findRoleById(RoleId roleId);

	Paging<Role> queryRoles(RolePaginationQuery pagingQuery);
}
