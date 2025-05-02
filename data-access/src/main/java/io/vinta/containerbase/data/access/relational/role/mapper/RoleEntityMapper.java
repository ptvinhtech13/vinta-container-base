package io.vinta.containerbase.data.access.relational.role.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.data.access.relational.role.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface RoleEntityMapper {
	RoleEntityMapper INSTANCE = Mappers.getMapper(RoleEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "featureNodeIds", source = "featureNodeIds", qualifiedByName = "featureNodeIdsToLongArray")
	@Mapping(target = "tenantId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	RoleEntity toUpdate(@MappingTarget RoleEntity existing, Role role);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "tenantId", source = "tenantId", qualifiedByName = "tenantIdToLong")
	@Mapping(target = "featureNodeIds", source = "featureNodeIds", qualifiedByName = "featureNodeIdsToLongArray")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	RoleEntity toCreate(Role role);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToRoleId")
	@Mapping(target = "tenantId", source = "tenantId", qualifiedByName = "longToTenantId")
	@Mapping(target = "featureNodeIds", source = "featureNodeIds", qualifiedByName = "longArrayToFeatureNodeIds")
	Role toModel(RoleEntity roleEntity);
}
