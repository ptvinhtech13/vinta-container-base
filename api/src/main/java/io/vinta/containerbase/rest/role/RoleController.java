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
package io.vinta.containerbase.rest.role;

import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.rest.api.RoleApi;
import io.vinta.containerbase.rest.role.mapper.RolePaginationMapper;
import io.vinta.containerbase.rest.role.mapper.RoleRequestMapper;
import io.vinta.containerbase.rest.role.mapper.RoleResponseMapper;
import io.vinta.containerbase.rest.role.request.CreateRoleRequest;
import io.vinta.containerbase.rest.role.request.QueryRolePaginationRequest;
import io.vinta.containerbase.rest.role.request.UpdateRoleRequest;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleApi {

	private final RoleCommandService roleCommandService;
	private final RoleQueryService roleQueryService;
	private final FeatureNodeQueryService featureNodeQueryService;

	@Override
	public RoleResponse getRole(Long roleId) {
		return RoleResponseMapper.INSTANCE.toResponse(featureNodeQueryService, roleQueryService.getRole(
				AppSecurityContextHolder.getTenantId(), MapstructCommonDomainMapper.INSTANCE.longToRoleId(roleId)));

	}

	@Override
	public RoleResponse getRoleKey(String roleKey) {
		return RoleResponseMapper.INSTANCE.toResponse(featureNodeQueryService, roleQueryService.getRoleKey(
				AppSecurityContextHolder.getTenantId(), roleKey));
	}

	@Override
	public RoleResponse createRole(CreateRoleRequest request) {
		return RoleResponseMapper.INSTANCE.toResponse(featureNodeQueryService, roleCommandService.createRole(
				RoleRequestMapper.INSTANCE.toCreate(AppSecurityContextHolder.getTenantId(), request)));

	}

	@Override
	public RoleResponse updateRole(Long roleId, UpdateRoleRequest request) {
		return RoleResponseMapper.INSTANCE.toResponse(featureNodeQueryService, roleCommandService.updateRole(
				RoleRequestMapper.INSTANCE.toUpdate(AppSecurityContextHolder.getTenantId(),
						MapstructCommonDomainMapper.INSTANCE.longToRoleId(roleId), request)));
	}

	@Override
	public Paging<RoleResponse> queryRole(QueryRolePaginationRequest request) {
		final var pagingQuery = RolePaginationMapper.INSTANCE.toPagingQuery(request);
		return RolePaginationMapper.INSTANCE.toPagingResponse(featureNodeQueryService, roleQueryService.queryRoles(
				pagingQuery.withFilter(Optional.ofNullable(pagingQuery.getFilter())
						.orElseGet(() -> FilterRoleQuery.builder()
								.build()))));
	}

	@Override
	public void deleteRole(Long roleId) {
		roleCommandService.deleteRole(AppSecurityContextHolder.getTenantId(), MapstructCommonDomainMapper.INSTANCE
				.longToRoleId(roleId));
	}
}
