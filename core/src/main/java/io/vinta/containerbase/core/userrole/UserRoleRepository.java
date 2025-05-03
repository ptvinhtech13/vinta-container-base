package io.vinta.containerbase.core.userrole;

import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import java.util.Set;

public interface UserRoleRepository {
	UserRole save(UserRole model);

	Set<UserRole> findSingleUserRoleByUserId(UserId userId);
}
