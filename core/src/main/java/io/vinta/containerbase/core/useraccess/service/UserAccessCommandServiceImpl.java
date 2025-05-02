package io.vinta.containerbase.core.useraccess.service;

import io.vinta.containerbase.core.useraccess.UserAccessCommandService;
import io.vinta.containerbase.core.useraccess.UserAccessRepository;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.mapper.UserAccessMapper;
import io.vinta.containerbase.core.users.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccessCommandServiceImpl implements UserAccessCommandService {
	private final UserAccessRepository repository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@EventListener
	public void handleUserCreatedEvent(UserCreatedEvent event) {
		final var command = event.getCreateUserAccessCommand();
		final var accessData = (UserAccessBasicAuthData) command.getAccessData();
		final var newCommand = command.withAccessData(accessData.withEncodedPassword(passwordEncoder.encode(accessData
				.getPassword()))
				.withPassword(null));
		repository.save(UserAccessMapper.INSTANCE.toModel(newCommand));
	}
}
