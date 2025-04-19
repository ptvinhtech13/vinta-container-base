package io.vinta.containerbase.core.importjob.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ImportJobMapper {
	ImportJobMapper INSTANCE = Mappers.getMapper(ImportJobMapper.class);

	@Mapping(target = "status", constant = "CREATED")
	@Mapping(target = "remark", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	ImportJob toEntity(CreateImportJobCommand command);
}
