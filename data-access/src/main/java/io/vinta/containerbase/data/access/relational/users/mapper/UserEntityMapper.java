package io.vinta.containerbase.data.access.relational.users.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.data.access.relational.userrole.mapper.UserRoleEntityMapper;
import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		UserRoleEntityMapper.class })
public interface UserEntityMapper {
	UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	UserEntity toUpdate(@MappingTarget UserEntity existing, User user);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToUserId")
	@Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "longToUserId")
	@Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "longToUserId")
	User toModel(UserEntity userEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	UserEntity toCreate(User user);

	@AfterMapping
	default void afterMappingCreateEntity(@MappingTarget UserEntity.UserEntityBuilder existing, User user) {
		existing.userRoles(user.getUserRoles()
				.stream()
				.map(userRole -> UserRoleEntityMapper.INSTANCE.toNewEntity(null, userRole))
				.collect(Collectors.toSet()));
	}
}
