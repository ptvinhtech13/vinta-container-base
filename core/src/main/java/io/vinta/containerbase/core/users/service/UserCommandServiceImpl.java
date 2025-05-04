package io.vinta.containerbase.core.users.service;

import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserRepository;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.event.UserCreatedEvent;
import io.vinta.containerbase.core.users.mapper.UserMapper;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.DeleteUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.core.users.request.UpdateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository repository;
	private final UserRoleRepository userRoleRepository;

	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User createUser(CreateUserCommand command) {
		if (repository.existUsers(FilterUserQuery.builder()
				.byEmail(command.getEmail())
				.byTenantId(command.getUserRole()
						.getTenantId())
				.build())) {
			throw new BadRequestException("User has existed");
		}
		final var vintaUser = repository.save(UserMapper.INSTANCE.toCreateModel(command));
		eventPublisher.publishEvent(UserCreatedEvent.builder()
				.user(vintaUser)
				.createUserAccessCommand(command.getUserAccess()
						.withUserId(vintaUser.getId()))
				.createUserRoleCommand(command.getUserRole()
						.withUserId(vintaUser.getId()))
				.build());

		return vintaUser.withUserRoles(userRoleRepository.findSingleUserRoleByUserId(vintaUser.getId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User updateUser(UpdateUserCommand command) {
		final var user = repository.findSingleUser(FilterUserQuery.builder()
				.byUserId(command.getUserId())
				.byTenantId(command.getTenantId())
				.build())
				.orElseThrow(() -> new NotFoundException("User not found"));

		return repository.save(UserMapper.INSTANCE.toUpdateProfile(user, command));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUsers(DeleteUserCommand command) {
		repository.deleteUsers(command);
	}

}
