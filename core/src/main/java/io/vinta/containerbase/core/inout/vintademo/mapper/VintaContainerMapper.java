package io.vinta.containerbase.core.inout.vintademo.mapper;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.containers.request.CreateContainerCommand;
import io.vinta.containerbase.core.inout.vintademo.models.VintaDemoContainerImportModelData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface VintaContainerMapper {
	VintaContainerMapper INSTANCE = Mappers.getMapper(VintaContainerMapper.class);

	@Mapping(target = "importJobId", source = "importJobId")
	@Mapping(target = "isReefer", ignore = true)
	CreateContainerCommand toCreateContainer(ImportJobId importJobId, VintaDemoContainerImportModelData data);
}
