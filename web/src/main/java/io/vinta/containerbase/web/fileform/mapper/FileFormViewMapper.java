package io.vinta.containerbase.web.fileform.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.web.fileform.views.FileFormView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface FileFormViewMapper {
	FileFormViewMapper INSTANCE = Mappers.getMapper(FileFormViewMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "fileFormIdToString")
	FileFormView toView(FileForm job);

}
