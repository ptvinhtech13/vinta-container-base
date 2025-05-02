package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.rest.tenant.request.CreateTenantRequest;
import io.vinta.containerbase.rest.tenant.request.QueryTenantPaginationRequest;
import io.vinta.containerbase.rest.tenant.response.TenantResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TenantApi {
	@GetMapping(path = "/api/tenant/tenants/{tenantId}")
	@ResponseStatus(HttpStatus.OK)
	TenantResponse getTenant(@PathVariable("tenantId") Long tenantId);

	@PostMapping(path = "/api/tenant/tenants")
	@ResponseStatus(HttpStatus.OK)
	TenantResponse createTenant(@Valid CreateTenantRequest request);

	@GetMapping(path = "/api/tenant/tenants")
	@ResponseStatus(HttpStatus.OK)
	Paging<TenantResponse> queryTenants(@ModelAttribute @Valid QueryTenantPaginationRequest request);
}
