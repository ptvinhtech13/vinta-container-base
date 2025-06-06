package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.security.domains.JwtTokenType;
import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.rest.access.request.LoginUserRequest;
import io.vinta.containerbase.rest.access.response.UserAccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserAccessApi {
	@PostMapping(path = "/api/user/access/login")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.PUBLIC)
	UserAccessResponse login(@RequestBody @Valid LoginUserRequest request);

	@PostMapping(path = "/api/user/access/refresh")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED, allowedTokenTypes = JwtTokenType.REFRESH_TOKEN)
	UserAccessResponse refreshToken();
}
