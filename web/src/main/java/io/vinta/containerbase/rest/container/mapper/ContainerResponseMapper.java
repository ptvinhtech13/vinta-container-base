package io.vinta.containerbase.rest.container.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.rest.container.response.ContainerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ContainerResponseMapper {
	ContainerResponseMapper INSTANCE = Mappers.getMapper(ContainerResponseMapper.class);

	@Mapping(source = "id", target = "id", qualifiedByName = "containerIdToString")
	@Mapping(source = "reefer", target = "isReefer")
	@Mapping(source = "importJobId", target = "importJobId", qualifiedByName = "importJobIdToString")
	ContainerResponse toResponse(Container container);
}
