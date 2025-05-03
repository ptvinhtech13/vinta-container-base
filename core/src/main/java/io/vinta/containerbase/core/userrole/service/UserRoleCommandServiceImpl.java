package io.vinta.containerbase.core.userrole.service;

import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.userrole.UserRoleCommandService;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import io.vinta.containerbase.core.userrole.mapper.UserRoleMapper;
import io.vinta.containerbase.core.users.event.UserCreatedEvent;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleCommandServiceImpl implements UserRoleCommandService {
	private final UserRoleRepository repository;
	private final RoleQueryService roleQueryService;

	@EventListener
	public void handleUserCreatedEvent(UserCreatedEvent event) {

		if (roleQueryService.queryRoles(RolePaginationQuery.builder()
				.filter(FilterRoleQuery.builder()
						.byTenantId(event.getCreateUserRoleCommand()
								.getTenantId())
						.byRoleIds(Set.of(event.getCreateUserRoleCommand()
								.getRoleId()))
						.build())
				.page(0)
				.size(1)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build())
				.getTotalElements() == 0) {
			throw new BadRequestException("Role not found");
		}

		repository.save(UserRoleMapper.INSTANCE.toCreate(event.getCreateUserRoleCommand()
				.withUserId(event.getUser()
						.getId())));
	}
}
