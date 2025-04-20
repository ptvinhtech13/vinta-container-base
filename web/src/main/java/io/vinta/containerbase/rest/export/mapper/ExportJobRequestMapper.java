package io.vinta.containerbase.rest.export.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;
import io.vinta.containerbase.rest.export.request.CreateExportJobRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ExportJobRequestMapper {
	ExportJobRequestMapper INSTANCE = Mappers.getMapper(ExportJobRequestMapper.class);

	@Mapping(target = "exportFormId", source = "exportFormId", qualifiedByName = "stringToFileFormId")
	CreateExportJobCommand toCreateExportJobCommand(CreateExportJobRequest request);
}
