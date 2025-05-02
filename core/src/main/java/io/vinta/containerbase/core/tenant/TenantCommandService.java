package io.vinta.containerbase.core.tenant;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.CreateTenantCommand;
import io.vinta.containerbase.core.tenant.request.UpdateTenantCommand;

public interface TenantCommandService {
	Tenant createTenant(CreateTenantCommand command);

	Tenant updateTenant(TenantId tenantId, UpdateTenantCommand command);

	void deleteTenant(TenantId tenantId);
}
