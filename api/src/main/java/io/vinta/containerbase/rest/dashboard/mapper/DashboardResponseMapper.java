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
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccess;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import io.vinta.containerbase.rest.dashboard.response.DashboardAccessPolicyResponse;
import io.vinta.containerbase.rest.dashboard.response.DashboardAccessResponse;
import io.vinta.containerbase.rest.dashboard.response.DashboardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface DashboardResponseMapper {
	DashboardResponseMapper INSTANCE = Mappers.getMapper(DashboardResponseMapper.class);

	@Mapping(target = "id", source = "id", qualifiedByName = "dashboardIdToString")
	DashboardResponse toResponse(Dashboard dashboard);

	@Mapping(target = "allowedTenantIds", source = "allowedTenantIds", qualifiedByName = "tenantIdsToStrings")
	@Mapping(target = "allowedUserIds", source = "allowedUserIds", qualifiedByName = "userIdsToStrings")
	DashboardAccessPolicyResponse toResponse(DashboardAccessPolicy source);

	DashboardAccessResponse toAccessResponse(DashboardAccess dashboardAccess);
}
