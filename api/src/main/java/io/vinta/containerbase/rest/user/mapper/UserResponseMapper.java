package io.vinta.containerbase.rest.user.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.rest.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserResponseMapper {
	UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

	UserResponse toResponse(User user);
}
