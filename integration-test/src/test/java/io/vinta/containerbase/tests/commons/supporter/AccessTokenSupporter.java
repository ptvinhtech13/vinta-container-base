package io.vinta.containerbase.tests.commons.supporter;

import io.vinta.containerbase.app.users.SuperAdminConfigProperties;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.core.useraccess.UserTokenAccessService;
import io.vinta.containerbase.core.useraccess.request.LoginCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessTokenSupporter {
	private final SuperAdminConfigProperties superAdminConfigProperties;
	private final UserTokenAccessService userTokenAccessService;

	public String loginAsSuperAdmin() {
		final var tokenPair = userTokenAccessService.login(

				LoginCommand.builder()
						.accessType(UserAccessType.BASIC_AUTH_PASSWORD)
						.email(superAdminConfigProperties.getEmail())
						.password(superAdminConfigProperties.getPassword())
						.build());
		return tokenPair.getAccessToken()
				.getToken();
	}
}
