package io.vinta.containerbase.core.users;

import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.FilterUserQuery;

public interface UserRepository {
	boolean existUsers(FilterUserQuery query);

	User save(User user);
}
