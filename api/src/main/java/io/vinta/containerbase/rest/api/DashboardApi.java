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
package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.rest.dashboard.request.CreateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardPaginationRequest;
import io.vinta.containerbase.rest.dashboard.request.UpdateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.response.DashboardAccessResponse;
import io.vinta.containerbase.rest.dashboard.response.DashboardResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface DashboardApi {
	@GetMapping(path = "/api/dashboard/dashboards/{dashboardId}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	DashboardResponse getDashboard(@PathVariable("dashboardId") Long dashboardId);

	@PostMapping(path = "/api/dashboard/dashboards")
	@ResponseStatus(HttpStatus.CREATED)
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	DashboardResponse createDashboard(@RequestBody @Valid CreateDashboardRequest request);

	@PostMapping(path = "/api/dashboard/dashboards/{dashboardId}/access")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	DashboardAccessResponse generateDashboardAccess(@PathVariable("dashboardId") Long dashboardId);

	@GetMapping(path = "/api/dashboard/dashboards")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	Paging<DashboardResponse> queryDashboards(@ModelAttribute @Valid QueryDashboardPaginationRequest request);

	@PutMapping(path = "/api/dashboard/dashboards/{dashboardId}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.OK)
	DashboardResponse updateDashboard(@PathVariable("dashboardId") Long dashboardId,
			@RequestBody @Valid UpdateDashboardRequest request);

	@DeleteMapping(path = "/api/dashboard/dashboards/{dashboardId}")
	@ContainerBaseApiAuthorized(security = PlatformApiSecurityLevel.AUTHENTICATED)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteDashboard(@PathVariable("dashboardId") Long dashboardId);
}
