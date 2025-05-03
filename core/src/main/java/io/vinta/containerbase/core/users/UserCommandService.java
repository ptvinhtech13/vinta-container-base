package io.vinta.containerbase.core.users;

import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.UpdateUserCommand;

public interface UserCommandService {
	User createUser(CreateUserCommand command);

	User updateUser(UpdateUserCommand command);
}
