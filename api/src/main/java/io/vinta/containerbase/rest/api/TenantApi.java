/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.rest.tenant.request.CreateTenantRequest;
import io.vinta.containerbase.rest.tenant.request.QueryTenantPaginationRequest;
import io.vinta.containerbase.rest.tenant.request.UpdateTenantRequest;
import io.vinta.containerbase.rest.tenant.response.TenantResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TenantApi {
	@GetMapping(path = "/api/tenant/tenants/{tenantId}")
	@ResponseStatus(HttpStatus.OK)
	TenantResponse getTenant(@PathVariable("tenantId") Long tenantId);

	@PostMapping(path = "/api/tenant/tenants")
	@ResponseStatus(HttpStatus.OK)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.PUBLIC)
	TenantResponse createTenant(@RequestBody @Valid CreateTenantRequest request);

	@GetMapping(path = "/api/tenant/tenants")
	@ResponseStatus(HttpStatus.OK)
	Paging<TenantResponse> queryTenants(@ModelAttribute @Valid QueryTenantPaginationRequest request);

	@PutMapping(path = "/api/tenant/tenants/{tenantId}")
	@ResponseStatus(HttpStatus.OK)
	TenantResponse updateTenant(@PathVariable("tenantId") Long tenantId,
			@RequestBody @Valid UpdateTenantRequest request);

	@DeleteMapping(path = "/api/tenant/tenants/{tenantId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteTenant(@PathVariable("tenantId") Long tenantId);
}
