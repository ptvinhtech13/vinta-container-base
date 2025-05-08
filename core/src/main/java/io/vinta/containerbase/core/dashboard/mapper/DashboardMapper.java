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
package io.vinta.containerbase.core.dashboard.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.request.CreateDashboardCommand;
import io.vinta.containerbase.core.dashboard.request.UpdateDashboardCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class })
public interface DashboardMapper {
	DashboardMapper INSTANCE = Mappers.getMapper(DashboardMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Dashboard toCreate(CreateDashboardCommand command);

	@Mapping(target = "id", source = "dashboard.id")
	@Mapping(target = "dashboardType", source = "dashboard.dashboardType")
	@Mapping(target = "name", source = "command.name")
	@Mapping(target = "description", source = "command.description")
	@Mapping(target = "status", source = "command.status")
	@Mapping(target = "metabaseId", source = "command.metabaseId")
	@Mapping(target = "accessPolicy", source = "command.accessPolicy")
	@Mapping(target = "createdAt", source = "dashboard.createdAt")
	@Mapping(target = "updatedAt", source = "dashboard.updatedAt")
	Dashboard toUpdate(Dashboard dashboard, UpdateDashboardCommand command);
}
