package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.request.UpdateUserRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserApi {
	@GetMapping(path = "/api/user/users/{userId}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	UserResponse getUser(@PathVariable("userId") Long userId);

	@PostMapping(path = "/api/user/users")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.CREATED)
	UserResponse createUser(@RequestBody @Valid CreateUserRequest request);

	@PutMapping(path = "/api/user/users/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	UserResponse updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UpdateUserRequest request);

	@GetMapping(path = "/api/user/users")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	Paging<UserResponse> queryUsers(@ModelAttribute @Valid QueryUserPaginationRequest request);
}
