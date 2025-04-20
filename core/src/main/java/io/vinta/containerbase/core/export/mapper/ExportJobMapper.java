package io.vinta.containerbase.core.export.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ExportJobMapper {
	ExportJobMapper INSTANCE = Mappers.getMapper(ExportJobMapper.class);

	@Mapping(target = "status", constant = "CREATED")
	@Mapping(target = "exportForm", source = "exportForm")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "totalContainer", ignore = true)
	@Mapping(target = "lastExportedPage", ignore = true)
	@Mapping(target = "totalPage", ignore = true)
	@Mapping(target = "totalExportedContainer", ignore = true)
	@Mapping(target = "fileOutputPath", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ExportJob toNewExportJob(CreateExportJobCommand command, FileForm exportForm);

}
