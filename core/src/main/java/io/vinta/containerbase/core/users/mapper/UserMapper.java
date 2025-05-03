package io.vinta.containerbase.core.users.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.UpdateUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	User toCreateModel(CreateUserCommand command);

	@Mapping(target = "id", source = "existing.id")
	@Mapping(target = "email", source = "command.email")
	@Mapping(target = "fullName", source = "command.fullName")
	@Mapping(target = "phoneNumber", source = "command.phoneNumber")
	@Mapping(target = "userStatus", source = "command.userStatus")
	@Mapping(target = "avatarPath", source = "command.avatarPath")
	@Mapping(target = "userType", source = "existing.userType")
	@Mapping(target = "createdAt", source = "existing.createdAt")
	@Mapping(target = "updatedAt", source = "existing.updatedAt")
	@Mapping(target = "createdBy", source = "existing.createdBy")
	@Mapping(target = "updatedBy", source = "existing.updatedBy")
	@Mapping(target = "userRoles", source = "existing.userRoles")
	User toUpdateProfile(User existing, UpdateUserCommand command);
}
