package io.vinta.containerbase.core.users.service;

import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
	private final UserRepository repository;
	//TODO: Vinh implements VintaUserQueryServiceImpl.java
}
