package io.vinta.containerbase.core.tenant.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.enums.TenantStatus;
import java.time.Instant;
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
public class Tenant extends BaseEntity<TenantId> {
	private final TenantId id;
	private final String title;
	private final String description;
	private final String domainHost;
	private final TenantStatus status;
	private final Instant createdAt;
	private final Instant updatedAt;
}