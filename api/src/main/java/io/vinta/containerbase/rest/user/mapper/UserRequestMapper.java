package io.vinta.containerbase.rest.user.mapper;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.UpdateUserCommand;
import io.vinta.containerbase.rest.user.request.CreateUserAccessBasicAuthRequest;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.UpdateUserRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserRequestMapper {
	UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

	@Mapping(target = "userAccess", ignore = true)
	@Mapping(target = "userRole", ignore = true)
	@Mapping(target = "userStatus", ignore = true)
	CreateUserCommand toCreate(@Context TenantId tenantId, CreateUserRequest request);

	@AfterMapping
	default void afterMapping(@MappingTarget CreateUserCommand.CreateUserCommandBuilder builder,
			CreateUserRequest request, @Context TenantId tenantId) {
		final var basicAuth = (CreateUserAccessBasicAuthRequest) request.getUserAccess();
		builder.userAccess(CreateUserAccessCommand.builder()
				.accessType(request.getUserAccess()
						.getAccessType())
				.accessData(UserAccessBasicAuthData.builder()
						.password(basicAuth.getPassword())
						.build())
				.build());

		builder.userRole(CreateUserRoleCommand.builder()
				.roleId(MapstructCommonDomainMapper.INSTANCE.stringToRoleId(request.getRoleId()))
				.tenantId(tenantId)
				.build());

	}

	@Mapping(target = "userId", source = "userId", qualifiedByName = "longToUserId")
	@Mapping(target = "tenantId", source = "tenantId")
	UpdateUserCommand toUpdate(Long userId, TenantId tenantId, UpdateUserRequest request);
}
