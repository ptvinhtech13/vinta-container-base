package io.vinta.containerbase.common.mapstruct;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.ContainerId;
import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.baseid.ImportRecordId;
import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

	@Named("stringToTenantId")
	default TenantId stringToTenantId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(TenantId::new)
				.orElse(null);
	}

	@Named("longToTenantId")
	default TenantId longToTenantId(Long source) {
		return Optional.ofNullable(source)
				.map(TenantId::new)
				.orElse(null);
	}

	@Named("tenantIdToString")
	default String tenantIdToString(TenantId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("tenantIdToLong")
	default Long tenantIdToLong(TenantId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

	@Named("stringToUserId")
	default UserId stringToUserId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(UserId::new)
				.orElse(null);
	}

	@Named("longToUserId")
	default UserId longToUserId(Long source) {
		return Optional.ofNullable(source)
				.map(UserId::new)
				.orElse(null);
	}

	@Named("longsToUserIds")
	default Set<UserId> longsToUserIds(Set<Long> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(UserId::new)
						.collect(Collectors.toSet()))
				.orElse(null);
	}

	@Named("userIdToString")
	default String userIdToString(UserId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("userIdToLong")
	default Long userIdToLong(UserId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

	@Named("stringToRoleId")
	default RoleId stringToRoleId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(RoleId::new)
				.orElse(null);
	}

	@Named("longToRoleId")
	default RoleId longToRoleId(Long source) {
		return Optional.ofNullable(source)
				.map(RoleId::new)
				.orElse(null);
	}

	@Named("roleIdToString")
	default String roleIdToString(RoleId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("roleIdToLong")
	default Long roleIdToLong(RoleId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

	@Named("stringsToRoleIds")
	default Set<RoleId> stringsToRoleIds(Set<String> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(Long::valueOf)
						.map(RoleId::new)
						.collect(Collectors.toSet()))
				.orElse(null);
	}

	@Named("longsToRoleIds")
	default Set<RoleId> longsToRoleIds(Set<Long> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(RoleId::new)
						.collect(Collectors.toSet()))
				.orElse(null);
	}

	@Named("stringToFeatureNodeId")
	default FeatureNodeId stringToFeatureNodeId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(FeatureNodeId::new)
				.orElse(null);
	}

	@Named("longToFeatureNodeId")
	default FeatureNodeId longToFeatureNodeId(Long source) {
		return Optional.ofNullable(source)
				.map(FeatureNodeId::new)
				.orElse(null);
	}

	@Named("longsToFeatureNodeIds")
	default Set<FeatureNodeId> longsToFeatureNodeIds(Set<Long> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(FeatureNodeId::new)
						.collect(Collectors.toSet()))
				.orElse(null);
	}

	@Named("featureNodeIdToString")
	default String featureNodeIdToString(FeatureNodeId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("featureNodeIdToLong")
	default Long featureNodeIdToLong(FeatureNodeId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.orElse(null);
	}

	@Named("featureNodeIdsToLongArray")
	default Long[] featureNodeIdsToLongArray(List<FeatureNodeId> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(BaseId::getValue)
						.toArray(Long[]::new))
				.orElse(null);
	}

	@Named("featureNodeIdsToStrings")
	default Set<String> featureNodeIdsToStrings(List<FeatureNodeId> source) {
		return Optional.ofNullable(source)
				.map(it -> it.stream()
						.map(BaseId::getValue)
						.map(String::valueOf)
						.collect(Collectors.toSet()))
				.orElse(null);
	}

	@Named("longArrayToFeatureNodeIds")
	default List<FeatureNodeId> longArrayToFeatureNodeIds(Long[] source) {
		return Optional.ofNullable(source)
				.map(it -> Stream.of(it)
						.map(FeatureNodeId::new)
						.collect(Collectors.toList()))
				.orElse(null);
	}
}
