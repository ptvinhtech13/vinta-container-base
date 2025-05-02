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
package io.vinta.containerbase.rest.tenant;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.tenant.TenantCommandService;
import io.vinta.containerbase.core.tenant.TenantQueryService;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.rest.api.TenantApi;
import io.vinta.containerbase.rest.tenant.mapper.TenantPaginationMapper;
import io.vinta.containerbase.rest.tenant.mapper.TenantRequestMapper;
import io.vinta.containerbase.rest.tenant.mapper.TenantResponseMapper;
import io.vinta.containerbase.rest.tenant.request.CreateTenantRequest;
import io.vinta.containerbase.rest.tenant.request.QueryTenantPaginationRequest;
import io.vinta.containerbase.rest.tenant.request.UpdateTenantRequest;
import io.vinta.containerbase.rest.tenant.response.TenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantController implements TenantApi {

	private final TenantCommandService tenantCommandService;
	private final TenantQueryService tenantQueryService;

	@Override
	public TenantResponse getTenant(Long tenantId) {
		Tenant tenant = tenantQueryService.getTenantById(new TenantId(tenantId));
		return TenantResponseMapper.INSTANCE.toResponse(tenant);
	}

	@Override
	public TenantResponse createTenant(CreateTenantRequest request) {
		Tenant tenant = tenantCommandService.createTenant(TenantRequestMapper.INSTANCE.toCreate(request));
		return TenantResponseMapper.INSTANCE.toResponse(tenant);
	}

	@Override
	public Paging<TenantResponse> queryTenants(QueryTenantPaginationRequest request) {
		Paging<Tenant> tenants = tenantQueryService.queryTenants(TenantPaginationMapper.INSTANCE.toPagingQuery(
				request));
		return tenants.map(TenantResponseMapper.INSTANCE::toResponse);
	}

	@Override
	public TenantResponse updateTenant(Long tenantId, UpdateTenantRequest request) {
		return TenantResponseMapper.INSTANCE.toResponse(tenantCommandService.updateTenant(
				MapstructCommonDomainMapper.INSTANCE.longToTenantId(tenantId), TenantRequestMapper.INSTANCE.toUpdate(
						request)));
	}

	@Override
	public void deleteTenant(Long tenantId) {

	}

}
