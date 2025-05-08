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
package io.vinta.containerbase.data.access.relational.dashboard.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import io.vinta.containerbase.data.access.relational.dashboard.entities.DashboardAccessDataPolicy;
import io.vinta.containerbase.data.access.relational.dashboard.entities.DashboardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface DashboardEntityMapper {
	DashboardEntityMapper INSTANCE = Mappers.getMapper(DashboardEntityMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	DashboardEntity toUpdate(@MappingTarget DashboardEntity existing, Dashboard dashboard);

	@Mapping(target = "id", source = "id", qualifiedByName = "longToDashboardId")
	Dashboard toModel(DashboardEntity dashboardEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	DashboardEntity toCreate(Dashboard dashboard);

	@Mapping(target = "allowedTenantIds", source = "allowedTenantIds", qualifiedByName = "tenantIdsToLongs")
	@Mapping(target = "allowedUserIds", source = "allowedUserIds", qualifiedByName = "userIdsToLongs")
	DashboardAccessDataPolicy toAccessPolicy(DashboardAccessPolicy source);

	@Mapping(target = "allowedTenantIds", source = "allowedTenantIds", qualifiedByName = "longsToTenantIds")
	@Mapping(target = "allowedUserIds", source = "allowedUserIds", qualifiedByName = "longsToUserIds")
	DashboardAccessPolicy toAccessPolicy(DashboardAccessDataPolicy source);
}
