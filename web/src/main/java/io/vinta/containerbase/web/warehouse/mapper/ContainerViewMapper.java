package io.vinta.containerbase.web.warehouse.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.web.warehouse.views.ContainerView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ContainerViewMapper {
	ContainerViewMapper INSTANCE = Mappers.getMapper(ContainerViewMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "containerIdToLong")
	@Mapping(target = "importJobId", source = "importJobId", qualifiedByName = "importJobIdToString")
	@Mapping(target = "isReefer", source = "reefer")
	ContainerView toView(Container container);

}
