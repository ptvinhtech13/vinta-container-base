package io.vinta.containerbase.core.tenant.service;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.tenant.TenantQueryService;
import io.vinta.containerbase.core.tenant.TenantRepository;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantQueryServiceImpl implements TenantQueryService {
	private final TenantRepository repository;

	@Override
	public Tenant getTenantById(TenantId tenantId) {
		return repository.findById(tenantId)
				.orElseThrow(() -> new NotFoundException("Tenant not found"));
	}

	@Override
	public Paging<Tenant> queryTenants(FindTenantQuery query) {
		return repository.queryTenants(query);
	}
}
