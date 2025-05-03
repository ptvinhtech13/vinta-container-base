package io.vinta.containerbase.core.users.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.UpdateUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
		MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface VintaUserMapper {
	VintaUserMapper INSTANCE = Mappers.getMapper(VintaUserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	User toCreateModel(CreateUserCommand command);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	User toUpdateProfile(@MappingTarget User user, UpdateUserCommand command);
}
