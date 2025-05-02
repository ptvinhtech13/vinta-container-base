package io.vinta.containerbase.core.users.service;

import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserRepository;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.event.UserCreatedEvent;
import io.vinta.containerbase.core.users.mapper.VintaUserMapper;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository repository;

	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User createUser(CreateUserCommand command) {
		if (repository.existUsers(FilterUserQuery.builder()
				.byEmail(command.getEmail())
				.byTenantId(command.getUserRole()
						.getTenantId())
				.build())) {
			throw new NotFoundException("User has existed");
		}
		final var vintaUser = repository.save(VintaUserMapper.INSTANCE.toCreateModel(command));
		eventPublisher.publishEvent(UserCreatedEvent.builder()
				.user(vintaUser)
				.createUserAccessCommand(command.getUserAccess()
						.withUserId(vintaUser.getId()))
				.createUserRoleCommand(command.getUserRole()
						.withUserId(vintaUser.getId()))
				.build());
		return vintaUser;
	}
}
