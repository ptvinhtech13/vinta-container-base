package io.vinta.containerbase.core.users.request;

import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateUserCommand {
	private final UserType userType;
	private final String email;
	private final String fullName;
	private final String avatarPath;
	private final String phoneNumber;

	@Builder.Default
	private final UserStatus userStatus = UserStatus.CREATED;

	private final CreateUserAccessCommand userAccess;
	private final CreateUserRoleCommand userRole;
}
