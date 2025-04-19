package io.vinta.containerbase.data.access.relational.containers.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.data.access.relational.containers.entities.ContainerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ContainerEntityMapper {
	ContainerEntityMapper INSTANCE = Mappers.getMapper(ContainerEntityMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToContainerId")
	@Mapping(target = "importJobId", source = "importJobId", qualifiedByName = "stringToImportJobId")
	Container toModel(ContainerEntity entity);

	@Mapping(target = "id", source = "id", qualifiedByName = "containerIdToLong")
	@Mapping(target = "importJobId", source = "importJobId", qualifiedByName = "importJobIdToString")
	@Mapping(target = "isReefer", source = "reefer")
	ContainerEntity toEntity(Container container);
}
