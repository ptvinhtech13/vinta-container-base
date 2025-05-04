package io.vinta.containerbase.core.users;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.DeleteUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.core.users.request.UserPaginationQuery;
import java.util.Optional;

public interface UserRepository {
	boolean existUsers(FilterUserQuery query);

	User save(User user);

	Optional<User> findSingleUser(FilterUserQuery query);

	Paging<User> queryUsers(UserPaginationQuery query);

	void deleteUsers(DeleteUserCommand command);
}
