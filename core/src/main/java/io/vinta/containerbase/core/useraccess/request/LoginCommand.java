package io.vinta.containerbase.core.useraccess.request;

import io.vinta.containerbase.common.enums.UserAccessType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LoginCommand {
	private final UserAccessType accessType;
	private final String email;
	private final String password;
}
