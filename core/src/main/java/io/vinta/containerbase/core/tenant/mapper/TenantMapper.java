package io.vinta.containerbase.core.tenant.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.CreateTenantCommand;
import io.vinta.containerbase.core.tenant.request.UpdateTenantCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
		MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface TenantMapper {
	TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "status", constant = "CREATED")
	Tenant toCreate(CreateTenantCommand command);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Tenant toUpdate(@MappingTarget Tenant tenant, UpdateTenantCommand command);
}
