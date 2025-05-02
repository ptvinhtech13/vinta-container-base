package io.vinta.containerbase.core.userrole.service;

import io.vinta.containerbase.core.userrole.UserRoleCommandService;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import io.vinta.containerbase.core.userrole.mapper.UserRoleMapper;
import io.vinta.containerbase.core.users.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleCommandServiceImpl implements UserRoleCommandService {
	private final UserRoleRepository repository;

	@EventListener
	public void handleUserCreatedEvent(UserCreatedEvent event) {
		repository.save(UserRoleMapper.INSTANCE.toCreate(event.getCreateUserRoleCommand()
				.withUserId(event.getUser()
						.getId())));
	}
}
