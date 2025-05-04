package io.vinta.containerbase.data.access.relational.users.repository;

import com.querydsl.core.types.Predicate;
import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.users.UserRepository;
import io.vinta.containerbase.core.users.entities.User;
import io.vinta.containerbase.core.users.request.DeleteUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.core.users.request.UserPaginationQuery;
import io.vinta.containerbase.data.access.relational.users.entities.QUserEntity;
import io.vinta.containerbase.data.access.relational.users.mapper.UserEntityMapper;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JpaUserRepository jpaUserRepository;

	@Override
	public boolean existUsers(FilterUserQuery query) {
		return jpaUserRepository.exists(buildUserPredicate(query));

	}

	private Predicate buildUserPredicate(FilterUserQuery query) {
		final var queryFilter = Optional.ofNullable(query)
				.orElseGet(() -> FilterUserQuery.builder()
						.build());
		return WhereBuilder.build()
				.applyIf(queryFilter.getByUserId() != null, where -> where.and(QUserEntity.userEntity.id.eq(queryFilter
						.getByUserId()
						.getValue())))
				.applyIf(queryFilter.getByUserStatuses() != null, where -> where.and(QUserEntity.userEntity.userStatus
						.in(queryFilter.getByUserStatuses())))
				.applyIf(queryFilter.getByUserTypes() != null, where -> where.and(QUserEntity.userEntity.userType.in(
						queryFilter.getByUserTypes())))
				.applyIf(queryFilter.getByTenantId() != null, where -> where.and(QUserEntity.userEntity.userRoles
						.any().tenantId.eq(queryFilter.getByTenantId()
								.getValue())))
				.applyIf(queryFilter.getByRoleIds() != null, where -> where.and(QUserEntity.userEntity.userRoles
						.any().roleId.in(queryFilter.getByRoleIds()
								.stream()
								.map(BaseId::getValue)
								.collect(Collectors.toSet()))))
				.applyIf(queryFilter.getByUserIds() != null, where -> where.and(QUserEntity.userEntity.id.in(queryFilter
						.getByUserIds()
						.stream()
						.map(BaseId::getValue)
						.collect(Collectors.toSet()))))
				.applyIf(queryFilter.getByCreatedRange() != null, where -> where.and(QUserEntity.userEntity.createdAt
						.after(queryFilter.getByCreatedRange()
								.from()))
						.and(QUserEntity.userEntity.createdAt.before(queryFilter.getByCreatedRange()
								.to())))
				.applyIf(queryFilter.getByPhoneNumber() != null, where -> where.and(QUserEntity.userEntity.phoneNumber
						.eq(queryFilter.getByPhoneNumber())))
				.applyIf(queryFilter.getByEmail() != null, where -> where.and(QUserEntity.userEntity.email.eq(
						queryFilter.getByEmail())))
				.applyIf(queryFilter.getByContainingEmail() != null, where -> where.and(QUserEntity.userEntity.email
						.containsIgnoreCase(queryFilter.getByContainingEmail())))
				.applyIf(queryFilter.getByName() != null, where -> where.and(QUserEntity.userEntity.fullName
						.containsIgnoreCase(queryFilter.getByName())))
				.and(jpaUserRepository.softDeletionPredicate());
	}

	@Override
	public User save(User user) {
		return Optional.ofNullable(user.getId())
				.map(it -> jpaUserRepository.findOne(buildUserPredicate((FilterUserQuery.builder()
						.byUserId(it)
						.build()))))
				.map(it -> it.orElseThrow(() -> new NotFoundException("User not found")))
				.map(existing -> UserEntityMapper.INSTANCE.toUpdate(existing, user))
				.map(jpaUserRepository::save)
				.map(UserEntityMapper.INSTANCE::toModel)
				.orElseGet(() -> UserEntityMapper.INSTANCE.toModel(jpaUserRepository.save(UserEntityMapper.INSTANCE
						.toCreate(user))));
	}

	@Override
	public Optional<User> findSingleUser(FilterUserQuery query) {
		return jpaUserRepository.findOne(buildUserPredicate(query))
				.map(UserEntityMapper.INSTANCE::toModel);
	}

	@Override
	public Paging<User> queryUsers(UserPaginationQuery query) {
		final var request = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterUserQuery.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = buildUserPredicate(request);

		final var pageResult = jpaUserRepository.findAllWithBase(predicate, pageable);
		return new Paging<>(pageResult.getContent()
				.stream()
				.map(UserEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}

	@Override
	public void deleteUsers(DeleteUserCommand command) {
		jpaUserRepository.deleteUserByTenantIdAndUserIds(command.getTenantId()
				.getValue(), command.getByUserIds()
						.stream()
						.map(BaseId::getValue)
						.collect(Collectors.toList()));
	}
}
