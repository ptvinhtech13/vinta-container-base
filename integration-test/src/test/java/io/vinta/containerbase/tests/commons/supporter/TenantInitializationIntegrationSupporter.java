package io.vinta.containerbase.tests.commons.supporter;

import io.vinta.containerbase.core.tenant.TenantCommandService;
import io.vinta.containerbase.core.tenant.TenantQueryService;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.CreateTenantCommand;
import io.vinta.containerbase.core.tenant.request.FilterTenantQuery;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TenantInitializationIntegrationSupporter {
	private final TenantCommandService tenantCommandService;

	private final TenantQueryService tenantQueryService;

	public Tenant initializeTenant(String domainHost) {
		final var tenantResult = tenantQueryService.queryTenants(FindTenantQuery.builder()
				.filter(FilterTenantQuery.builder()
						.byDomainHost(domainHost)
						.build())
				.page(0)
				.size(1)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build());
		if (tenantResult.getContent()
				.isEmpty()) {
			return tenantCommandService.createTenant(CreateTenantCommand.builder()
					.title("Test Tenant Title " + domainHost)
					.description("Test Tenant Description")
					.domainHost(domainHost)
					.build());
		} else {
			return tenantResult.getContent()
					.getFirst();
		}
	}
}
