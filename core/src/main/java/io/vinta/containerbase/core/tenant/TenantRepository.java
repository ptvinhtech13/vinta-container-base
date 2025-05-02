package io.vinta.containerbase.core.tenant;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.FilterTenantQuery;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;
import java.util.Optional;

public interface TenantRepository {
	Tenant save(Tenant tenant);

	Optional<Tenant> findById(TenantId tenantId);

	Paging<Tenant> queryTenants(FindTenantQuery query);

	boolean existsTenant(FilterTenantQuery query);
}
