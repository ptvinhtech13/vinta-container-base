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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.vinta.containerbase.common.baseid.DashboardId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.dashboard.DashboardQueryService;
import io.vinta.containerbase.core.dashboard.DashboardRepository;
import io.vinta.containerbase.core.dashboard.config.MetabaseConfigProperties;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccess;
import io.vinta.containerbase.core.dashboard.request.FindDashboardQuery;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardQueryServiceImpl implements DashboardQueryService {
	private final DashboardRepository repository;

	private final MetabaseConfigProperties metabaseConfigProperties;

	@Override
	public Paging<Dashboard> queryDashboard(FindDashboardQuery query) {
		return repository.queryDashboard(query);
	}

	@Override
	public Optional<Dashboard> getDashboard(DashboardId dashboardId) {
		return repository.findOneByDashboardId(dashboardId);
	}

	@Override
	public DashboardAccess getDashboardAccess(TenantId tenantId, DashboardId dashboardId) {

		final var dashboard = repository.findOneByDashboardId(dashboardId)
				.orElseThrow(() -> new NotFoundException("Dashboard not found"));

		final var livedTimeInMillis = Instant.now()
				.plus(metabaseConfigProperties.getAccessTokenTimeToLive());

		final var algorithm = Algorithm.HMAC256(metabaseConfigProperties.getAccessSecret());
		final var token = JWT.create()
				.withPayload(Map.of("resource", Map.of("dashboard", dashboard.getMetabaseId()), "params", Map.of(
						"tenant_id", tenantId.getValue())))
				.withExpiresAt(livedTimeInMillis)
				.sign(algorithm);

		return DashboardAccess.builder()
				.accessDashboardUrl("%s/%s".formatted(metabaseConfigProperties.getHostUrl(), token))
				.build();
	}
}
