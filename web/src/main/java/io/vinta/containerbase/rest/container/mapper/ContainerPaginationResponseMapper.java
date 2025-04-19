package io.vinta.containerbase.rest.container.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.rest.container.response.ContainerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		ContainerResponseMapper.class })
public interface ContainerPaginationResponseMapper {
	ContainerPaginationResponseMapper INSTANCE = Mappers.getMapper(ContainerPaginationResponseMapper.class);

	Paging<ContainerResponse> toResponse(Paging<Container> page);
}
