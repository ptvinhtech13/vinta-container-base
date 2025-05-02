package io.vinta.containerbase.core.useraccess.service;

import io.vinta.containerbase.core.useraccess.UserAccessQueryService;
import io.vinta.containerbase.core.useraccess.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccessQueryServiceImpl implements UserAccessQueryService {
	private final UserAccessRepository repository;
}
