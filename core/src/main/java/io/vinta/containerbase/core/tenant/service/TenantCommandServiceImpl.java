package io.vinta.containerbase.core.tenant.service;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.enums.TenantStatus;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.core.tenant.TenantCommandService;
import io.vinta.containerbase.core.tenant.TenantRepository;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.mapper.TenantMapper;
import io.vinta.containerbase.core.tenant.request.CreateTenantCommand;
import io.vinta.containerbase.core.tenant.request.FilterTenantQuery;
import io.vinta.containerbase.core.tenant.request.UpdateTenantCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantCommandServiceImpl implements TenantCommandService {
	private final TenantRepository repository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Tenant createTenant(CreateTenantCommand command) {
		if (repository.existsTenant(FilterTenantQuery.builder()
				.byDomainHost(command.getDomainHost())
				.build())) {
			throw new IllegalArgumentException("Tenant with domain host " + command.getDomainHost()
					+ " already exists");
		}

		return repository.save(TenantMapper.INSTANCE.toCreate(command));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Tenant updateTenant(TenantId tenantId, UpdateTenantCommand command) {
		Tenant tenant = repository.findById(tenantId)
				.orElseThrow(() -> new NotFoundException("Tenant not found"));

		// Check if domain host is being changed and if it's already in use
		if (!tenant.getDomainHost()
				.equals(command.getDomainHost()) && repository.existsTenant(FilterTenantQuery.builder()
						.byDomainHost(command.getDomainHost())
						.build())) {
			throw new IllegalArgumentException("Tenant with domain host " + command.getDomainHost()
					+ " already exists");
		}

		return repository.save(TenantMapper.INSTANCE.toUpdate(tenant, command));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTenant(TenantId tenantId) {
		Tenant tenant = repository.findById(tenantId)
				.orElseThrow(() -> new NotFoundException("Tenant not found"));

		// Soft delete by changing status to ARCHIVED
		Tenant archivedTenant = tenant.withStatus(TenantStatus.ARCHIVED);
		repository.save(archivedTenant);
	}
}
