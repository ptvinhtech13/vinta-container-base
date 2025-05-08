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
package io.vinta.containerbase.rest.dashboard.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.request.FindDashboardQuery;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardPaginationRequest;
import io.vinta.containerbase.rest.dashboard.response.DashboardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		DashboardResponseMapper.class, DashboardRequestMapper.class, })
public interface DashboardPaginationMapper {
	DashboardPaginationMapper INSTANCE = Mappers.getMapper(DashboardPaginationMapper.class);

	FindDashboardQuery toPagingQuery(QueryDashboardPaginationRequest source);

	Paging<DashboardResponse> toPagingResponse(Paging<Dashboard> source);

}
