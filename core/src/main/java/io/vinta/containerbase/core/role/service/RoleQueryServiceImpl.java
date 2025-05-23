package io.vinta.containerbase.core.role.service;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.RoleRepository;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleQueryServiceImpl implements RoleQueryService {

	private final RoleRepository repository;

	@Override
	public Role getRole(TenantId tenantId, RoleId roleId) {
		return repository.findRoleById(tenantId, roleId)
				.orElseThrow(() -> new NotFoundException("Role not found"));
	}

	@Override
	public Paging<Role> queryRoles(RolePaginationQuery pagingQuery) {
		return repository.queryRoles(pagingQuery);
	}

	@Override
	public Role getRoleKey(TenantId tenantId, String roleKey) {
		return repository.findRoleByKey(tenantId, roleKey)
				.orElseThrow(() -> new NotFoundException("Role not found"));
	}
}
