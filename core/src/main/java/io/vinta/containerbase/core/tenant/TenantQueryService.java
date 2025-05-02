package io.vinta.containerbase.core.tenant;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;

public interface TenantQueryService {
	Tenant getTenantById(TenantId tenantId);

	Paging<Tenant> queryTenants(FindTenantQuery query);
}
