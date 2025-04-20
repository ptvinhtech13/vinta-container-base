package io.vinta.containerbase.data.access.relational.exportjob.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import io.vinta.containerbase.data.access.relational.exportjob.entities.ExportJobEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ExportJobEntityMapper {
	ExportJobEntityMapper INSTANCE = Mappers.getMapper(ExportJobEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "filterContainer", ignore = true)
	@Mapping(target = "exportFormId", source = "exportForm.id", qualifiedByName = "fileFormIdToString")
	ExportJobEntity toUpdateEntity(@MappingTarget ExportJobEntity existing, ExportJob job);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToExportJobId")
	@Mapping(target = "exportForm", expression = "java(fileFormManagerService.getFileForm(mapstructCommonDomainMapper.stringToFileFormId(exportJobEntity.getExportFormId())).orElse(null))")
	ExportJob toModel(@Context FileFormManagerService fileFormManagerService, ExportJobEntity exportJobEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "exportFormId", source = "exportForm.id", qualifiedByName = "fileFormIdToString")
	ExportJobEntity toNewEntity(ExportJob job);
}
