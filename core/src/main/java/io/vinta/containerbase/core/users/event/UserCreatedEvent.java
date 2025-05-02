package io.vinta.containerbase.core.users.event;

import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import io.vinta.containerbase.core.users.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class UserCreatedEvent {
	private final User user;
	private final CreateUserAccessCommand createUserAccessCommand;
	private final CreateUserRoleCommand createUserRoleCommand;
}
