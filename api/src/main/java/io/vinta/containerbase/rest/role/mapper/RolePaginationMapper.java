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
package io.vinta.containerbase.rest.role.mapper;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.common.mapstruct.MapstructConfig;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.rest.role.request.QueryRolePaginationRequest;
import io.vinta.containerbase.rest.role.request.QueryRoleRequest;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class, uses = { MapstructCommonMapper.class, MapstructCommonDomainMapper.class,
		RoleResponseMapper.class, RoleRequestMapper.class })
public interface RolePaginationMapper {
	RolePaginationMapper INSTANCE = Mappers.getMapper(RolePaginationMapper.class);

	RolePaginationQuery toPagingQuery(QueryRolePaginationRequest source);

	Paging<RoleResponse> toPagingResponse(@Context FeatureNodeQueryService featureNodeQueryService,
			Paging<Role> source);

	@Mapping(target = "byRoleIds", source = "byRoleIds", qualifiedByName = "stringsToRoleIds")
	@Mapping(target = "byTenantId", ignore = true)
	FilterRoleQuery toFilter(QueryRoleRequest query);
}
