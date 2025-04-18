package io.vinta.containerbase.common.mapstruct;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.ContainerId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class)
public interface MapstructCommonDomainMapper {

	MapstructCommonDomainMapper INSTANCE = Mappers.getMapper(MapstructCommonDomainMapper.class);

	@Named("stringToImportJobId")
	default ImportJobId stringToImportJobId(String source) {
		return Optional.ofNullable(source)
				.map(ImportJobId::new)
				.orElse(null);
	}

	@Named("importJobIdToString")
	default String importJobIdToString(ImportJobId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("stringToContainerId")
	default ContainerId stringToContainerId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(ContainerId::new)
				.orElse(null);
	}

	@Named("longToContainerId")
	default ContainerId longToContainerId(Long source) {
		return Optional.ofNullable(source)
				.map(ContainerId::new)
				.orElse(null);
	}

	@Named("containerIdToString")
	default String containerIdToString(ContainerId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("containerIdToLong")
	default Long containerIdToLong(ContainerId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

}
