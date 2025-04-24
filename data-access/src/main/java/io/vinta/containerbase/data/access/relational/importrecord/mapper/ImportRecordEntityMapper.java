package io.vinta.containerbase.data.access.relational.importrecord.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.data.access.relational.importrecord.entities.ImportRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ImportRecordEntityMapper {
	ImportRecordEntityMapper INSTANCE = Mappers.getMapper(ImportRecordEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "importJobId", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ImportRecordEntity toUpdateEntity(@MappingTarget ImportRecordEntity existing, ImportRecord record);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "importJobId", source = "importJobId", qualifiedByName = "importJobIdToString")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ImportRecordEntity toNewEntity(ImportRecord record);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToImportRecordId")
	@Mapping(target = "importJobId", source = "importJobId", qualifiedByName = "stringToImportJobId")
	ImportRecord toModel(ImportRecordEntity entity);
}
