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
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import io.vinta.containerbase.core.dashboard.request.CreateDashboardCommand;
import io.vinta.containerbase.core.dashboard.request.FilterDashboardQuery;
import io.vinta.containerbase.core.dashboard.request.UpdateDashboardCommand;
import io.vinta.containerbase.rest.dashboard.request.CreateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.DashboardAccessPolicyRequest;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.UpdateDashboardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface DashboardRequestMapper {
	DashboardRequestMapper INSTANCE = Mappers.getMapper(DashboardRequestMapper.class);

	CreateDashboardCommand toCreate(CreateDashboardRequest request);

	UpdateDashboardCommand toUpdate(UpdateDashboardRequest request);

	@Mapping(target = "byTenantIds", source = "byTenantIds", qualifiedByName = "stringsToTenantIds")
	FilterDashboardQuery toFilterDashboardQuery(QueryDashboardRequest request);

	@Mapping(target = "allowedTenantIds", source = "allowedTenantIds", qualifiedByName = "stringsToTenantIds")
	@Mapping(target = "allowedUserIds", source = "allowedUserIds", qualifiedByName = "stringsToUserIds")
	DashboardAccessPolicy toRequest(DashboardAccessPolicyRequest source);
}
