package io.vinta.containerbase.core.useraccess.entities;

import io.vinta.containerbase.common.accesstoken.UserAccessToken;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class UserAccessTokenPair {
	private final UserAccessToken accessToken;
	private final UserAccessToken refreshToken;
}