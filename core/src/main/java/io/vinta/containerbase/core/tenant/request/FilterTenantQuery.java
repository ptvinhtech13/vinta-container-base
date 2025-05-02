package io.vinta.containerbase.core.tenant.request;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.enums.TenantStatus;
import io.vinta.containerbase.common.range.InstantDateTimeRange;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@With
@Getter
@Builder
@RequiredArgsConstructor
public class FilterTenantQuery {
	private final TenantId byTenantId;
	private final Set<TenantId> byTenantIds;
	private final Set<TenantStatus> byTenantStatuses;
	private final String byTitle;
	private final String byContainingTitle;
	private final String byDomainHost;
	private final InstantDateTimeRange byCreatedRange;
}
