package io.vinta.containerbase.core.role.request;

import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateRoleCommand {
	private final RoleId roleId;
	private final TenantId tenantId;
	private final String title;
	private final String description;
	private final Set<FeatureNodeId> featureNodeIds;

}