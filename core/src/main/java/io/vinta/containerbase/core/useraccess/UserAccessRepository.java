package io.vinta.containerbase.core.useraccess;

import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.core.useraccess.entities.UserAccess;
import java.util.Optional;

public interface UserAccessRepository {
	UserAccess save(UserAccess model);

	Optional<UserAccess> findUserAccess(UserAccessType accessType, UserId userId);
}
