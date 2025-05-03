package io.vinta.containerbase.core.tenant.events;

import io.vinta.containerbase.core.tenant.entities.Tenant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TenantCreatedEvent {
	private final Tenant tenant;
}
