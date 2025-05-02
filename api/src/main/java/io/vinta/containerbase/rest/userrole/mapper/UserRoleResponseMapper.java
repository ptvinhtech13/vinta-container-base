package io.vinta.containerbase.rest.userrole.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import io.vinta.containerbase.rest.userrole.response.UserRoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserRoleResponseMapper {
	UserRoleResponseMapper INSTANCE = Mappers.getMapper(UserRoleResponseMapper.class);

	@Mapping(target = "tenantId", source = "tenantId", qualifiedByName = "tenantIdToString")
	@Mapping(target = "roleId", source = "roleId", qualifiedByName = "roleIdToString")
	UserRoleResponse toResponse(UserRole userRole);
}
