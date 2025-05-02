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
package io.vinta.containerbase.rest.tenant.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;
import io.vinta.containerbase.rest.tenant.request.QueryTenantPaginationRequest;
import io.vinta.containerbase.rest.tenant.response.TenantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		TenantResponseMapper.class, TenantRequestMapper.class, })
public interface TenantPaginationMapper {
	TenantPaginationMapper INSTANCE = Mappers.getMapper(TenantPaginationMapper.class);

	FindTenantQuery toPagingQuery(QueryTenantPaginationRequest source);

	Paging<TenantResponse> toPagingResponse(Paging<Tenant> source);

}
