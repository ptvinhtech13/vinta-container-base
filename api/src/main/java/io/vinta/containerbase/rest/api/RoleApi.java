package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.rest.role.request.CreateRoleRequest;
import io.vinta.containerbase.rest.role.request.QueryRolePaginationRequest;
import io.vinta.containerbase.rest.role.request.UpdateRoleRequest;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface RoleApi {

	@GetMapping(path = "/api/role/roles/{roleId}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	RoleResponse getRole(@PathVariable("roleId") Long roleId);

	@GetMapping(path = "/api/role/roles/keys/{roleKey}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	RoleResponse getRoleKey(@PathVariable("roleKey") String roleKey);

	@PostMapping(path = "/api/role/roles")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.CREATED)
	RoleResponse createRole(@RequestBody @Valid CreateRoleRequest request);

	@PutMapping(path = "/api/role/roles/{roleId}")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	RoleResponse updateRole(@PathVariable("roleId") Long roleId, @RequestBody @Valid UpdateRoleRequest request);

	@GetMapping(path = "/api/role/roles")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	Paging<RoleResponse> queryRole(@ModelAttribute @Valid QueryRolePaginationRequest request);

	@DeleteMapping(path = "/api/role/roles/{roleId}")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	void deleteRole(@PathVariable("roleId") Long roleId);

}
