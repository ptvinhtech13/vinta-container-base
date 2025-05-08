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
package io.vinta.containerbase.core.dashboard;

import io.vinta.containerbase.common.baseid.DashboardId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.request.FindDashboardQuery;
import java.util.Optional;

public interface DashboardRepository {
	Dashboard save(Dashboard dashboard);

	void deleteDashboard(DashboardId dashboardId);

	Paging<Dashboard> queryDashboard(FindDashboardQuery query);

	Optional<Dashboard> findOneByDashboardId(DashboardId dashboardId);
}
