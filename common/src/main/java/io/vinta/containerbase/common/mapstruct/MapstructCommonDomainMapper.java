package io.vinta.containerbase.common.mapstruct;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.ContainerId;
import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.baseid.ImportRecordId;
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

	@Named("stringToFileFormId")
	default FileFormId stringToFileFormId(String source) {
		return Optional.ofNullable(source)
				.map(FileFormId::new)
				.orElse(null);
	}

	@Named("fileFormIdToString")
	default String fileFormIdToString(FileFormId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("stringToExportJobId")
	default ExportJobId stringToExportJobId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(ExportJobId::new)
				.orElse(null);
	}

	@Named("longToExportJobId")
	default ExportJobId longToExportJobId(Long source) {
		return Optional.ofNullable(source)
				.map(ExportJobId::new)
				.orElse(null);
	}

	@Named("exportJobIdToString")
	default String exportJobIdToString(ExportJobId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("exportJobIdToLong")
	default Long exportJobIdToLong(ExportJobId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

	@Named("stringToImportRecordId")
	default ImportRecordId stringToImportRecordId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(ImportRecordId::new)
				.orElse(null);
	}

	@Named("longToImportRecordId")
	default ImportRecordId longToImportRecordId(Long source) {
		return Optional.ofNullable(source)
				.map(ImportRecordId::new)
				.orElse(null);
	}

	@Named("importRecordIdToString")
	default String importRecordIdToString(ImportRecordId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("importRecordIdToLong")
	default Long importRecordIdToLong(ImportRecordId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}
}
