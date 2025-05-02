package io.vinta.containerbase.core.role.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
@ToString
public class Role extends BaseEntity<RoleId> {
	private final RoleId id;
	private final TenantId tenantId;
	private final String title;
	private final String roleKey;
	private final String description;
	private final List<FeatureNodeId> featureNodeIds;
	private final Instant createdAt;
	private final Instant updatedAt;
}