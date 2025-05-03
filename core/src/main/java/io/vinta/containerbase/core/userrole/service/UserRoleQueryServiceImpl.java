package io.vinta.containerbase.core.userrole.service;

import io.vinta.containerbase.core.userrole.UserRoleQueryService;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleQueryServiceImpl implements UserRoleQueryService {
	private final UserRoleRepository repository;

}
