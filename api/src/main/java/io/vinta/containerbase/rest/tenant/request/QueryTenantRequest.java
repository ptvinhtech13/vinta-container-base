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
package io.vinta.containerbase.rest.tenant.request;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.enums.TenantStatus;
import io.vinta.containerbase.common.range.InstantDateTimeRange;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class QueryTenantRequest {
	private final TenantId byTenantId;
	private final Set<TenantId> byTenantIds;
	private final Set<TenantStatus> byTenantStatuses;
	private final String byTitle;
	private final String byContainingTitle;
	private final String byDomainHost;
	private final InstantDateTimeRange byCreatedRange;
}
