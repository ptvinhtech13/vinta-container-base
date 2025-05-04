package io.vinta.containerbase.core.userrole.service;

import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.userrole.UserRoleCommandService;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleCommandServiceImpl implements UserRoleCommandService {
	private final UserRoleRepository repository;
	private final RoleQueryService roleQueryService;

}
