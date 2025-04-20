package io.vinta.containerbase.app.fileform.mapper;

import io.vinta.containerbase.app.fileform.config.FileFormConfigProperties;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface FileFormConfigPropertiesMapper {
	FileFormConfigPropertiesMapper INSTANCE = Mappers.getMapper(FileFormConfigPropertiesMapper.class);

	@Mapping(target = "id", source = "fileFormId", qualifiedByName = "stringToFileFormId")
	FileForm toFileForm(FileFormConfigProperties.FileFormConfig fileFormConfig);
}
