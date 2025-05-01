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

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.rest.api.TenantApi;
import io.vinta.containerbase.rest.tenant.request.QueryTenantPaginationRequest;
import io.vinta.containerbase.rest.tenant.response.TenantResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController implements TenantApi {

	@Override
	public TenantResponse getTenant(Long tenantId) {
		return null;//TODO: write method getTenant

	}

	@Override
	public Paging<TenantResponse> queryTenants(QueryTenantPaginationRequest request) {
		return null;//TODO: write method queryTenants

	}
}
