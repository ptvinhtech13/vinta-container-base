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
package io.vinta.containerbase.rest.user.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.core.users.request.UserPaginationQuery;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.request.QueryUserRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		UserResponseMapper.class, UserRequestMapper.class })
public interface UserPaginationMapper {
	UserPaginationMapper INSTANCE = Mappers.getMapper(UserPaginationMapper.class);

	UserPaginationQuery toPagingQuery(QueryUserPaginationRequest source);

	Paging<UserResponse> toPagingResponse(Paging<User> source);

	@Mapping(target = "byTenantId", source = "byTenantId", qualifiedByName = "longToTenantId")
	@Mapping(target = "byUserId", source = "byUserId", qualifiedByName = "longToUserId")
	@Mapping(target = "byRoleIds", source = "byRoleIds", qualifiedByName = "longsToRoleIds")
	@Mapping(target = "byUserIds", source = "byUserIds", qualifiedByName = "longsToUserIds")
	FilterUserQuery toFilter(QueryUserRequest query);
}
