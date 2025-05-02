package io.vinta.containerbase.core.useraccess;

import io.vinta.containerbase.core.useraccess.entities.UserAccess;

public interface UserAccessRepository {
	UserAccess save(UserAccess model);
}
