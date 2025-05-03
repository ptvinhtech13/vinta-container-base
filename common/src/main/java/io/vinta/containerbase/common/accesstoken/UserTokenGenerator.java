package io.vinta.containerbase.common.accesstoken;

import io.vinta.containerbase.common.security.domains.JwtTokenClaim;

public interface UserTokenGenerator {
	UserAccessToken generateToken(final JwtTokenClaim tokenClaim);
}
