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
package io.vinta.containerbase.rest.user;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.rest.api.UserApi;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

	@Override
	public UserResponse getUser(Long userId) {
		return null;//TODO: write method getUser

	}

	@Override
	public UserResponse createUser(CreateUserRequest request) {
		return null;//TODO: write method createUser

	}

	@Override
	public Paging<UserResponse> queryUsers(QueryUserPaginationRequest request) {
		return null;//TODO: write method queryUsers

	}
}
