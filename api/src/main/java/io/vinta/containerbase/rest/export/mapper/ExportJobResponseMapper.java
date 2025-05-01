package io.vinta.containerbase.rest.export.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.rest.export.response.ExportJobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		FormFileResponseMapper.class })
public interface ExportJobResponseMapper {
	ExportJobResponseMapper INSTANCE = Mappers.getMapper(ExportJobResponseMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "exportJobIdToString")
	ExportJobResponse toResponse(ExportJob exportJob);
}
