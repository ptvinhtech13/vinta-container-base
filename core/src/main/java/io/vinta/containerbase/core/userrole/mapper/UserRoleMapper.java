package io.vinta.containerbase.core.userrole.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import io.vinta.containerbase.core.userrole.request.CreateUserRoleCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface UserRoleMapper {
	UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	UserRole toCreate(CreateUserRoleCommand command);
}
