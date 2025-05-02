package io.vinta.containerbase.data.access.relational.useraccess.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.useraccess.entities.UserAccess;
import io.vinta.containerbase.data.access.relational.useraccess.entities.UserAccessEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserAccessEntityMapper {
	UserAccessEntityMapper INSTANCE = Mappers.getMapper(UserAccessEntityMapper.class);

	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserAccessEntity toUpdate(@MappingTarget UserAccessEntity existing, UserAccess model);

	@Mapping(target = "userId", source = "userId", qualifiedByName = "userIdToLong")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserAccessEntity toNewEntity(UserAccess model);

	@Mapping(target = "userId", source = "userId", qualifiedByName = "longToUserId")
	UserAccess toModel(UserAccessEntity entity);
}
