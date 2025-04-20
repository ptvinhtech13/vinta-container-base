package io.vinta.containerbase.web.export.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.web.export.views.ExportJobView;
import io.vinta.containerbase.web.fileform.mapper.FileFormViewMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		FileFormViewMapper.class })
public interface ExportJobViewMapper {
	ExportJobViewMapper INSTANCE = Mappers.getMapper(ExportJobViewMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "exportJobIdToLong")
	ExportJobView toView(ExportJob job);

}
