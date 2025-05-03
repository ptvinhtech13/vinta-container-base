package io.vinta.containerbase.rest.access.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.useraccess.entities.UserAccessTokenPair;
import io.vinta.containerbase.rest.access.response.UserAccessResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserAccessResponseMapper {
	UserAccessResponseMapper INSTANCE = Mappers.getMapper(UserAccessResponseMapper.class);

	UserAccessResponse toResponse(UserAccessTokenPair source);
}
