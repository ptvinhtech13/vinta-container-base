package io.vinta.containerbase.core.featurenodes;

import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.security.permissions.ApiPermissionKey;
import java.util.List;

public interface FeatureNodeQueryService {
	List<ApiPermissionKey> getChildrenNodeByParentPath(String parentPath);

	List<ApiPermissionKey> getFeatureNodes(List<FeatureNodeId> featureNodeIds);
}
