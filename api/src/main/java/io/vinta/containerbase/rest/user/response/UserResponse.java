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
package io.vinta.containerbase.rest.user.response;

import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.rest.userrole.response.UserRoleResponse;
import java.time.Instant;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserResponse {
	private final String id;
	private final UserType userType;
	private final UserStatus userStatus;
	private final String phoneNumber;
	private final String email;
	private final String fullName;
	private final String avatarPath;

	private final Set<UserRoleResponse> userRoles;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final String createdBy;
	private final String updatedBy;
}
