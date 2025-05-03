package io.vinta.containerbase.core.tenant.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.CreateTenantCommand;
import io.vinta.containerbase.core.tenant.request.UpdateTenantCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface TenantMapper {
	TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "status", constant = "CREATED")
	Tenant toCreate(CreateTenantCommand command);

	@Mapping(target = "id", source = "existing.id")
	@Mapping(target = "status", source = "command.status")
	@Mapping(target = "title", source = "command.title")
	@Mapping(target = "description", source = "command.description")
	@Mapping(target = "domainHost", source = "command.domainHost")
	@Mapping(target = "createdAt", source = "existing.createdAt")
	@Mapping(target = "updatedAt", source = "existing.createdAt")
	Tenant toUpdate(Tenant existing, UpdateTenantCommand command);
}
