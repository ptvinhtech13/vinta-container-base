package io.vinta.containerbase.rest.user.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.rest.user.response.UserResponse;
import io.vinta.containerbase.rest.userrole.mapper.UserRoleResponseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		UserRoleResponseMapper.class })
public interface UserResponseMapper {
	UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "userIdToString")
	@Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userIdToString")
	@Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "userIdToString")
	UserResponse toResponse(User user);
}
