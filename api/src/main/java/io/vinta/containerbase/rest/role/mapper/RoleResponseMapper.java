package io.vinta.containerbase.rest.role.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface RoleResponseMapper {
	RoleResponseMapper INSTANCE = Mappers.getMapper(RoleResponseMapper.class);

	RoleResponse toResponse(Role role);

}
