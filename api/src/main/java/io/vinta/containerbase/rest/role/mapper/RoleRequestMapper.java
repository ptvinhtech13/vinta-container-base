package io.vinta.containerbase.rest.role.mapper;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;
import io.vinta.containerbase.rest.role.request.CreateRoleRequest;
import io.vinta.containerbase.rest.role.request.UpdateRoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface RoleRequestMapper {
	RoleRequestMapper INSTANCE = Mappers.getMapper(RoleRequestMapper.class);

	@Mapping(target = "featureNodeIds", source = "request.featureNodeIds", qualifiedByName = "longsToFeatureNodeIds")
	@Mapping(target = "roleKey", ignore = true)
	CreateRoleCommand toCreate(TenantId tenantId, CreateRoleRequest request);

	@Mapping(target = "featureNodeIds", source = "request.featureNodeIds", qualifiedByName = "longsToFeatureNodeIds")
	UpdateRoleCommand toUpdate(TenantId tenantId, RoleId roleId, UpdateRoleRequest request);
}
