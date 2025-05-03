package io.vinta.containerbase.common.accesstoken;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserAccessToken {
	private final String token;
	private final Instant expiresAt;
}
