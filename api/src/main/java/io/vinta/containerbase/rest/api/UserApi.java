package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserApi {
	@GetMapping(path = "/api/user/users/{userId}")
	@ResponseStatus(HttpStatus.OK)
	UserResponse getUser(@PathVariable("userId") Long userId);

	@PostMapping(path = "/api/user/users")
	@ResponseStatus(HttpStatus.CREATED)
	UserResponse createUser(@ModelAttribute @Valid CreateUserRequest request);

	@GetMapping(path = "/api/user/users")
	@ResponseStatus(HttpStatus.OK)
	Paging<UserResponse> queryUsers(@ModelAttribute @Valid QueryUserPaginationRequest request);
}
