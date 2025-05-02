package io.vinta.containerbase.data.access.relational.tenant.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.data.access.relational.tenant.entities.TenantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface TenantEntityMapper {
	TenantEntityMapper INSTANCE = Mappers.getMapper(TenantEntityMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "tenantIdToLong")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	TenantEntity toCreate(Tenant tenant);

	@Mapping(target = "id", source = "id", qualifiedByName = "tenantIdToLong")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	TenantEntity toUpdate(@MappingTarget TenantEntity entity, Tenant tenant);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToTenantId")
	Tenant toModel(TenantEntity entity);
}
