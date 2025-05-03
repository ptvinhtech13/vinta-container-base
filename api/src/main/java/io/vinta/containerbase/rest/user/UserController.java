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

import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.rest.api.UserApi;
import io.vinta.containerbase.rest.user.mapper.UserPaginationMapper;
import io.vinta.containerbase.rest.user.mapper.UserRequestMapper;
import io.vinta.containerbase.rest.user.mapper.UserResponseMapper;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.request.UpdateUserRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserCommandService userCommandService;

	private final UserQueryService userQueryService;

	@Override
	public UserResponse getUser(Long userId) {
		return UserResponseMapper.INSTANCE.toResponse(userQueryService.findSingleUser(FilterUserQuery.builder()
				.byUserId(MapstructCommonDomainMapper.INSTANCE.longToUserId(userId))
				.byTenantId(AppSecurityContextHolder.getTenantId())
				.build())
				.orElseThrow(() -> new NotFoundException("User not found")));
	}

	@Override
	public UserResponse createUser(CreateUserRequest request) {
		return UserResponseMapper.INSTANCE.toResponse(userCommandService.createUser(UserRequestMapper.INSTANCE.toCreate(
				AppSecurityContextHolder.getTenantId(), request)));

	}

	@Override
	public Paging<UserResponse> queryUsers(QueryUserPaginationRequest request) {
		final var pagingQuery = UserPaginationMapper.INSTANCE.toPagingQuery(request);
		return UserPaginationMapper.INSTANCE.toPagingResponse(userQueryService.queryUsers(pagingQuery.withFilter(
				Optional.ofNullable(pagingQuery.getFilter())
						.orElseGet(() -> FilterUserQuery.builder()
								.build())
						.withByTenantId(AppSecurityContextHolder.getTenantId()))));
	}

	@Override
	public UserResponse updateUser(Long userId, UpdateUserRequest request) {
		return UserResponseMapper.INSTANCE.toResponse(userCommandService.updateUser(UserRequestMapper.INSTANCE.toUpdate(
				userId, AppSecurityContextHolder.getTenantId(), request)));

	}
}
