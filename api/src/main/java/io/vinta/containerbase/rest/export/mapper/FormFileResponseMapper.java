package io.vinta.containerbase.rest.export.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.rest.export.response.FileFormResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface FormFileResponseMapper {
	FormFileResponseMapper INSTANCE = Mappers.getMapper(FormFileResponseMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "fileFormIdToString")
	FileFormResponse toResponse(FileForm fileForm);
}
