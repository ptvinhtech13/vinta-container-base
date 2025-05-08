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
package io.vinta.containerbase.core.dashboard.service;

import io.vinta.containerbase.common.baseid.DashboardId;
import io.vinta.containerbase.common.constants.TenantConstants;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.core.dashboard.DashboardCommandService;
import io.vinta.containerbase.core.dashboard.DashboardQueryService;
import io.vinta.containerbase.core.dashboard.DashboardRepository;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import io.vinta.containerbase.core.dashboard.mapper.DashboardMapper;
import io.vinta.containerbase.core.dashboard.request.CreateDashboardCommand;
import io.vinta.containerbase.core.dashboard.request.UpdateDashboardCommand;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardCommandServiceImpl implements DashboardCommandService {

	private final DashboardRepository repository;
	private final DashboardQueryService queryService;
	private final static DashboardAccessPolicy DEFAULT_ACCESS_POLICY = DashboardAccessPolicy.builder()
			.allowedTenantIds(Set.of(MapstructCommonDomainMapper.INSTANCE.longToTenantId(
					TenantConstants.ADMIN_TENANT_ID)))
			.build();

	@Override
	public Dashboard createDashboard(CreateDashboardCommand command) {
		final var dashboard = DashboardMapper.INSTANCE.toCreate(command);
		return repository.save(appendAdminTenant(dashboard));

	}

	private Dashboard appendAdminTenant(Dashboard dashboard) {
		if (dashboard.getAccessPolicy() == null) {
			return dashboard.withAccessPolicy(DEFAULT_ACCESS_POLICY);
		}

		return dashboard.withAccessPolicy(dashboard.getAccessPolicy()
				.withAllowedTenantIds(Stream.concat(CollectionUtils.isEmpty(dashboard.getAccessPolicy()
						.getAllowedTenantIds())
								? Stream.empty()
								: dashboard.getAccessPolicy()
										.getAllowedTenantIds()
										.stream(), Stream.of(MapstructCommonDomainMapper.INSTANCE.longToTenantId(
												TenantConstants.ADMIN_TENANT_ID)))
						.collect(Collectors.toSet())));
	}

	@Override
	public Dashboard updateDashboard(DashboardId dashboardId, UpdateDashboardCommand command) {
		final var dashboard = queryService.getDashboard(dashboardId)
				.orElseThrow(() -> new NotFoundException("Dashboard not found"));
		return repository.save(appendAdminTenant(DashboardMapper.INSTANCE.toUpdate(dashboard, command)));

	}

	@Override
	public void deleteDashboard(DashboardId dashboardId) {
		repository.deleteDashboard(dashboardId);
	}
}
