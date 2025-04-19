package io.vinta.containerbase.rest.importjob.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.rest.importjob.response.ImportJobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ImportJobResponseMapper {
	ImportJobResponseMapper INSTANCE = Mappers.getMapper(ImportJobResponseMapper.class);

	@Mapping(source = "id", target = "id", qualifiedByName = "importJobIdToString")
	ImportJobResponse toResponse(ImportJob job);
}
