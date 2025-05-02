package io.vinta.containerbase.data.access.relational.userrole.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import io.vinta.containerbase.data.access.relational.userrole.entities.UserRoleEntity;
import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserRoleEntityMapper {
	UserRoleEntityMapper INSTANCE = Mappers.getMapper(UserRoleEntityMapper.class);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "roleId", source = "roleId", qualifiedByName = "roleIdToLong")
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserRoleEntity toUpdate(@MappingTarget UserRoleEntity existing, UserRole model);

	@Mapping(target = "user", source = "userEntity")
	@Mapping(target = "roleId", source = "model.roleId", qualifiedByName = "roleIdToLong")
	@Mapping(target = "tenantId", source = "model.tenantId", qualifiedByName = "tenantIdToLong")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserRoleEntity toNewEntity(UserEntity userEntity, UserRole model);

	@Mapping(target = "userId", source = "user.id", qualifiedByName = "longToUserId")
	@Mapping(target = "tenantId", source = "tenantId", qualifiedByName = "longToTenantId")
	@Mapping(target = "roleId", source = "roleId", qualifiedByName = "longToRoleId")
	UserRole toModel(UserRoleEntity entity);
}
