package io.vinta.containerbase.core.userrole.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class UserRole extends BaseEntity<UserId> {
	private final UserId userId;
	private final TenantId tenantId;
	private final RoleId roleId;
	private final Instant createdAt;
	private final Instant updatedAt;
}