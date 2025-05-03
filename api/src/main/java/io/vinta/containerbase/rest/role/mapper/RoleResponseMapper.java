package io.vinta.containerbase.rest.role.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.rest.role.response.FeatureNodeResponse;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface RoleResponseMapper {
	RoleResponseMapper INSTANCE = Mappers.getMapper(RoleResponseMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "roleIdToString")
	@Mapping(target = "featureNodes", ignore = true)
	RoleResponse toResponse(@Context FeatureNodeQueryService featureNodeQueryService, Role role);

	@AfterMapping
	default void afterMapping(@MappingTarget RoleResponse.RoleResponseBuilder builder,
			@Context FeatureNodeQueryService featureNodeQueryService, Role role) {
		builder.featureNodes(featureNodeQueryService.getFeatureNodes(role.getFeatureNodeIds())
				.stream()
				.map(key -> FeatureNodeResponse.builder()
						.id(key.getId()
								.getValue()
								.toString())
						.nodePath(key.getNodePath())
						.nodeTitle(key.getNodeTitle())
						.nodeType(key.getNodeType())
						.displayOrder(key.getDisplayOrder())
						.allowedUserTypes(key.getAllowedUserTypes())
						.build())
				.collect(Collectors.toSet()));
	}
}
