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
package io.vinta.containerbase.core.dashboard.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.DashboardId;
import io.vinta.containerbase.common.baseid.DashboardType;
import io.vinta.containerbase.common.enums.DashboardStatus;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class Dashboard extends BaseEntity<DashboardId> {
	private final DashboardId id;
	private final DashboardType dashboardType;
	private final String name;
	private final String description;
	private final Long metabaseId;
	private final DashboardStatus status;
	private final DashboardAccessPolicy accessPolicy;
	private final Instant createdAt;
	private final Instant updatedAt;
}