package io.vinta.containerbase.core.useraccess.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.useraccess.entities.UserAccess;
import io.vinta.containerbase.core.useraccess.request.CreateUserAccessCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserAccessMapper {
	UserAccessMapper INSTANCE = Mappers.getMapper(UserAccessMapper.class);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserAccess toModel(CreateUserAccessCommand newCommand);
}
