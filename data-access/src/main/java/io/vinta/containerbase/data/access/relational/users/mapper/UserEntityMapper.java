package io.vinta.containerbase.data.access.relational.users.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserEntityMapper {
	UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserEntity toUpdate(@MappingTarget UserEntity existing, User user);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToUserId")
	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "longToUserId")
	@Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "longToUserId")
	User toModel(UserEntity userEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserEntity toCreate(User user);
}
