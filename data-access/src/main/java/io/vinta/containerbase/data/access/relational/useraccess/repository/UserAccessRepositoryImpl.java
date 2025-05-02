package io.vinta.containerbase.data.access.relational.useraccess.repository;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.core.useraccess.UserAccessRepository;
import io.vinta.containerbase.core.useraccess.entities.UserAccess;
import io.vinta.containerbase.data.access.relational.useraccess.mapper.UserAccessEntityMapper;
import io.vinta.containerbase.data.access.relational.users.repository.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessRepositoryImpl implements UserAccessRepository {

	private final JpaUserAccessRepository jpaUserAccessRepository;
	private final JpaUserRepository jpaUserRepository;

	@Override
	public UserAccess save(UserAccess model) {
		return Optional.ofNullable(model.getUserId())
				.map(BaseId::getValue)
				.map(userId -> {
					final var entity = jpaUserAccessRepository.findById(userId)
							.map(existing -> UserAccessEntityMapper.INSTANCE.toUpdate(existing, model))
							.orElseGet(() -> UserAccessEntityMapper.INSTANCE.toNewEntity(model));
					return jpaUserAccessRepository.save(entity);
				})
				.map(UserAccessEntityMapper.INSTANCE::toModel)
				.orElseThrow(() -> new BadRequestException("User Id is required"));

	}
}
