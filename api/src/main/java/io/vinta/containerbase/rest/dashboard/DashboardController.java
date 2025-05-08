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
package io.vinta.containerbase.rest.dashboard;

import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.context.AppSecurityContext;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.core.dashboard.DashboardCommandService;
import io.vinta.containerbase.core.dashboard.DashboardQueryService;
import io.vinta.containerbase.core.dashboard.request.FilterDashboardQuery;
import io.vinta.containerbase.rest.api.DashboardApi;
import io.vinta.containerbase.rest.dashboard.mapper.DashboardPaginationMapper;
import io.vinta.containerbase.rest.dashboard.mapper.DashboardRequestMapper;
import io.vinta.containerbase.rest.dashboard.mapper.DashboardResponseMapper;
import io.vinta.containerbase.rest.dashboard.request.CreateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardPaginationRequest;
import io.vinta.containerbase.rest.dashboard.request.UpdateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.response.DashboardAccessResponse;
import io.vinta.containerbase.rest.dashboard.response.DashboardResponse;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DashboardController implements DashboardApi {
	private final DashboardQueryService queryService;
	private final DashboardCommandService commandService;

	@Override
	public DashboardResponse getDashboard(Long dashboardId) {
		return queryService.getDashboard(MapstructCommonDomainMapper.INSTANCE.longToDashboardId(dashboardId))
				.map(DashboardResponseMapper.INSTANCE::toResponse)
				.orElseThrow(() -> new BadRequestException("Dashboard not found"));

	}

	@Override
	public DashboardResponse createDashboard(@Valid CreateDashboardRequest request) {
		return DashboardResponseMapper.INSTANCE.toResponse(commandService.createDashboard(
				DashboardRequestMapper.INSTANCE.toCreate(request)));
	}

	@Override
	public DashboardAccessResponse generateDashboardAccess(Long dashboardId) {
		return DashboardResponseMapper.INSTANCE.toAccessResponse(queryService.getDashboardAccess(
				AppSecurityContextHolder.getTenantId(), MapstructCommonDomainMapper.INSTANCE.longToDashboardId(
						dashboardId)));
	}

	@Override
	public Paging<DashboardResponse> queryDashboards(QueryDashboardPaginationRequest request) {
		final var findDashboardQuery = DashboardPaginationMapper.INSTANCE.toPagingQuery(request);
		final var baseUserAccessFilter = FilterDashboardQuery.builder()
				.byTenantIds(Set.of(AppSecurityContextHolder.getTenantId()))
				.byUserTypes(Set.of(AppSecurityContextHolder.getContext()
						.map(AppSecurityContext::getUserType)
						.orElseThrow(() -> new BadRequestException("User Type is required"))))
				.byUserIds(Set.of(AppSecurityContextHolder.getContext()
						.map(AppSecurityContext::getUserId)
						.orElseThrow(() -> new BadRequestException("UserId is required"))))
				.build();

		final var dashboardFilter = Optional.ofNullable(findDashboardQuery.getFilter())
				.map(filter -> baseUserAccessFilter.withByTenantIds(Stream.concat(CollectionUtils.isEmpty(filter
						.getByTenantIds())
								? Stream.empty()
								: filter.getByTenantIds()
										.stream(), baseUserAccessFilter.getByTenantIds()
												.stream())
						.collect(Collectors.toSet()))
						.withByUserTypes(Stream.concat(CollectionUtils.isEmpty(filter.getByUserTypes())
								? Stream.empty()
								: filter.getByUserTypes()
										.stream(), baseUserAccessFilter.getByUserTypes()
												.stream())
								.collect(Collectors.toSet()))
						.withByUserIds(Stream.concat(CollectionUtils.isEmpty(filter.getByUserIds())
								? Stream.empty()
								: filter.getByUserIds()
										.stream(), baseUserAccessFilter.getByUserIds()
												.stream())
								.collect(Collectors.toSet())))
				.orElse(baseUserAccessFilter);

		return DashboardPaginationMapper.INSTANCE.toPagingResponse(queryService.queryDashboard(findDashboardQuery
				.withFilter(dashboardFilter)));

	}

	@Override
	public DashboardResponse updateDashboard(Long dashboardId, @Valid UpdateDashboardRequest request) {
		return DashboardResponseMapper.INSTANCE.toResponse(commandService.updateDashboard(
				MapstructCommonDomainMapper.INSTANCE.longToDashboardId(dashboardId), DashboardRequestMapper.INSTANCE
						.toUpdate(request)));
	}

	@Override
	public void deleteDashboard(Long dashboardId) {
		commandService.deleteDashboard(MapstructCommonDomainMapper.INSTANCE.longToDashboardId(dashboardId));
	}
}
