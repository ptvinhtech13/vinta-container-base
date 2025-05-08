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
package io.vinta.containerbase.core.dashboard.request;

import io.vinta.containerbase.common.enums.DashboardStatus;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateDashboardCommand {
	@NotEmpty
	private final String name;

	private final String description;

	private final Long metabaseId;

	@Builder.Default
	private final DashboardStatus status = DashboardStatus.DISABLED;

	@Valid
	@NotNull
	private final DashboardAccessPolicy accessPolicy;
}