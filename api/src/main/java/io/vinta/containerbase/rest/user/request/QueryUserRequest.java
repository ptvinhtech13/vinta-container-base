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
package io.vinta.containerbase.rest.user.request;

import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.range.InstantDateTimeRange;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryUserRequest {
	private Long byUserId;
	private Long byTenantId;
	private Set<Long> byRoleIds;
	private Set<UserType> byUserTypes;
	private Set<UserStatus> byUserStatuses;
	private Set<Long> byUserIds;
	private String byPhoneNumber;
	private String byEmail;
	private String byContainingEmail;
	private String byName;
	private String byRealmId;
	private InstantDateTimeRange byCreatedRange;
}
