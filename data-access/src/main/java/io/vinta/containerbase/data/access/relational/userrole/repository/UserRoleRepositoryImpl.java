package io.vinta.containerbase.data.access.relational.userrole.repository;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.core.userrole.UserRoleRepository;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import io.vinta.containerbase.data.access.relational.userrole.entities.QUserRoleEntity;
import io.vinta.containerbase.data.access.relational.userrole.entities.UserRoleId;
import io.vinta.containerbase.data.access.relational.userrole.mapper.UserRoleEntityMapper;
import io.vinta.containerbase.data.access.relational.users.repository.JpaUserRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {
	private final JpaUserRoleRepository jpaUserRoleRepository;
	private final JpaUserRepository jpaUserRepository;

	@Override
	public UserRole save(UserRole model) {
		return Optional.ofNullable(model.getUserId())
				.map(BaseId::getValue)
				.map(userId -> jpaUserRepository.findById(userId)
						.orElseThrow(() -> new BadRequestException("User not found")))
				.map(userEntity -> {
					final var entity = jpaUserRoleRepository.findById(UserRoleId.builder()
							.user(userEntity)
							.tenantId(model.getTenantId()
									.getValue())
							.build())
							.map(existing -> UserRoleEntityMapper.INSTANCE.toUpdate(existing, model))
							.orElseGet(() -> UserRoleEntityMapper.INSTANCE.toNewEntity(userEntity, model));
					return jpaUserRoleRepository.save(entity);
				})
				.map(UserRoleEntityMapper.INSTANCE::toModel)
				.orElseThrow(() -> new BadRequestException("User Id is required"));
	}

	@Override
	public Set<UserRole> findSingleUserRoleByUserId(UserId userId) {
		return jpaUserRoleRepository.findAllWithBase(QUserRoleEntity.userRoleEntity.user.id.eq(userId.getValue()))
				.stream()
				.map(UserRoleEntityMapper.INSTANCE::toModel)
				.collect(Collectors.toSet());
	}
}
