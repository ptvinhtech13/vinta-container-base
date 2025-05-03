package io.vinta.containerbase.core.role.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.core.role.request.UpdateRoleCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
		MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Role toCreate(CreateRoleCommand command);

	@Mapping(target = "id", source = "role.id")
	@Mapping(target = "title", source = "command.title")
	@Mapping(target = "description", source = "command.description")
	@Mapping(target = "featureNodeIds", source = "command.featureNodeIds")
	@Mapping(target = "tenantId", source = "role.tenantId")
	@Mapping(target = "createdAt", source = "role.createdAt")
	@Mapping(target = "updatedAt", source = "role.updatedAt")
	Role toUpdate(Role role, UpdateRoleCommand command);
}
