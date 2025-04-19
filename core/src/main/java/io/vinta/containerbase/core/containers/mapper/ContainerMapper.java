package io.vinta.containerbase.core.containers.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.CreateContainerCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ContainerMapper {
	ContainerMapper INSTANCE = Mappers.getMapper(ContainerMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "importJobId", source = "importJobId")
	@Mapping(target = "isReefer", source = "reefer")
	Container toCreateContainer(CreateContainerCommand command);
}
