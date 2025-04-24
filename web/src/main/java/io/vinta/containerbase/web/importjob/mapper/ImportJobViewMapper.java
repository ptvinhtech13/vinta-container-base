package io.vinta.containerbase.web.importjob.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.web.importjob.views.ImportJobView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface ImportJobViewMapper {
	ImportJobViewMapper INSTANCE = Mappers.getMapper(ImportJobViewMapper.class);

	@Mapping(target = "fileFormId", source = "fileFormId", qualifiedByName = "fileFormIdToString")
	ImportJobView toView(ImportJob job);
}
