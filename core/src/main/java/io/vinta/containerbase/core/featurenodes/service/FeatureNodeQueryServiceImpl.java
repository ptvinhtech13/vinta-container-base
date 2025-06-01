package io.vinta.containerbase.core.featurenodes.service;

import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.security.permissions.ApiPermissionKey;
import io.vinta.containerbase.common.security.permissions.FeatureNodeType;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class FeatureNodeQueryServiceImpl implements FeatureNodeQueryService {

	@Override
	public List<ApiPermissionKey> getChildrenNodeByParentPath(String parentPath) {
		return Arrays.stream(ApiPermissionKey.values())
				.filter(it -> FeatureNodeType.API.equals(it.getNodeType()))
				.filter(it -> !parentPath.equals(it.getNodePath()) && it.getNodePath()
						.startsWith(parentPath))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApiPermissionKey> getFeatureNodes(List<FeatureNodeId> featureNodeIds) {
		return CollectionUtils.isEmpty(featureNodeIds)
				? Collections.emptyList()
				: Arrays.stream(ApiPermissionKey.values())
						.filter(it -> featureNodeIds.contains(it.getId()))
						.collect(Collectors.toList());
	}

	@Override
	public List<ApiPermissionKey> getModuleNodes() {
		return Arrays.stream(ApiPermissionKey.values())
				.filter(it -> FeatureNodeType.MODULE.equals(it.getNodeType()))
				.collect(Collectors.toList());
	}

}
