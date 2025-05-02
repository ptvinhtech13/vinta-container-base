package io.vinta.containerbase.data.access.relational.role.repository;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.role.RoleRepository;
import io.vinta.containerbase.core.role.entities.Role;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.data.access.relational.role.entities.QRoleEntity;
import io.vinta.containerbase.data.access.relational.role.mapper.RoleEntityMapper;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
	private final JpaRoleRepository jpaRoleRepository;

	@Override
	public Role save(Role role) {
		return Optional.ofNullable(role.getId())
				.map(BaseId::getValue)
				.map(jpaRoleRepository::findById)
				.map(it -> it.orElseThrow(() -> new NotFoundException("Role not found")))
				.map(existing -> RoleEntityMapper.INSTANCE.toUpdate(existing, role))
				.map(jpaRoleRepository::save)
				.map(RoleEntityMapper.INSTANCE::toModel)
				.orElseGet(() -> RoleEntityMapper.INSTANCE.toModel(jpaRoleRepository.save(RoleEntityMapper.INSTANCE
						.toCreate(role))));
	}

	@Override
	public Optional<Role> findRoleById(RoleId roleId) {
		return Optional.ofNullable(roleId)
				.map(BaseId::getValue)
				.map(jpaRoleRepository::findById)
				.flatMap(it -> it.map(RoleEntityMapper.INSTANCE::toModel));

	}

	@Override
	public Paging<Role> queryRoles(RolePaginationQuery query) {

		final var request = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterRoleQuery.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build()
				.applyIf(!CollectionUtils.isEmpty(request.getByRoleIds()), where -> where.and(QRoleEntity.roleEntity.id
						.in(request.getByRoleIds()
								.stream()
								.map(BaseId::getValue)
								.collect(Collectors.toSet()))))
				.applyIf(request.getByTenantId() != null, where -> where.and(QRoleEntity.roleEntity.tenantId.eq(request
						.getByTenantId()
						.getValue())));

		final var pageResult = jpaRoleRepository.findAllWithBase(predicate, pageable);
		return new Paging<>(pageResult.getContent()
				.stream()
				.map(RoleEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}
}
