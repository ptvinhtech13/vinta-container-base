package io.vinta.containerbase.core.tenant.request;

import io.vinta.containerbase.common.enums.TenantStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class CreateTenantCommand {
	private final String title;
	private final String description;
	private final String domainHost;
	private final TenantStatus status;
}