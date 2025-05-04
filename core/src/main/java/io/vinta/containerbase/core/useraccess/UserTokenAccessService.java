package io.vinta.containerbase.core.useraccess;

import io.vinta.containerbase.core.useraccess.entities.UserAccessTokenPair;
import io.vinta.containerbase.core.useraccess.request.LoginCommand;
import io.vinta.containerbase.core.useraccess.request.RefreshTokenCommand;

public interface UserTokenAccessService {
	UserAccessTokenPair login(LoginCommand loginCommand);

	UserAccessTokenPair refreshToken(RefreshTokenCommand command);
}
