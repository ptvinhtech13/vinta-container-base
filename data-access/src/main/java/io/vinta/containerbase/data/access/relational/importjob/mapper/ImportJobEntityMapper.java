package io.vinta.containerbase.data.access.relational.importjob.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.data.access.relational.importjob.entities.ImportJobEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ImportJobEntityMapper {
	ImportJobEntityMapper INSTANCE = Mappers.getMapper(ImportJobEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "fileFormId", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ImportJobEntity toUpdateEntity(@MappingTarget ImportJobEntity existing, ImportJob job);

	@Mapping(target = "id", source = "id", qualifiedByName = "importJobIdToString")
	@Mapping(target = "fileFormId", source = "fileFormId", qualifiedByName = "fileFormIdToString")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ImportJobEntity toNewEntity(ImportJob job);

	@Mapping(target = "id", source = "id", qualifiedByName = "stringToImportJobId")
	@Mapping(target = "fileFormId", source = "fileFormId", qualifiedByName = "stringToFileFormId")
	ImportJob toModel(ImportJobEntity entity);
}
