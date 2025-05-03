package io.vinta.containerbase.core.users.service;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.UserRepository;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.core.users.request.UserPaginationQuery;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
	private final UserRepository repository;

	@Override
	public Optional<User> findSingleUser(FilterUserQuery query) {
		return repository.findSingleUser(query);
	}

	@Override
	public Paging<User> queryUsers(UserPaginationQuery query) {
		return repository.queryUsers(query);

	}
}
