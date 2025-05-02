package io.vinta.containerbase.data.access.relational.tenant.repository;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.tenant.TenantRepository;
import io.vinta.containerbase.core.tenant.entities.Tenant;
import io.vinta.containerbase.core.tenant.request.FilterTenantQuery;
import io.vinta.containerbase.core.tenant.request.FindTenantQuery;
import io.vinta.containerbase.data.access.relational.tenant.entities.QTenantEntity;
import io.vinta.containerbase.data.access.relational.tenant.mapper.TenantEntityMapper;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {
	private final JpaTenantRepository jpaTenantRepository;

	@Override
	public Tenant save(Tenant tenant) {
		return Optional.ofNullable(tenant.getId())
				.map(BaseId::getValue)
				.map(jpaTenantRepository::findById)
				.map(it -> it.orElseThrow(() -> new NotFoundException("Tenant not found")))
				.map(existing -> TenantEntityMapper.INSTANCE.toUpdate(existing, tenant))
				.map(jpaTenantRepository::save)
				.map(TenantEntityMapper.INSTANCE::toModel)
				.orElseGet(() -> TenantEntityMapper.INSTANCE.toModel(jpaTenantRepository.save(
						TenantEntityMapper.INSTANCE.toCreate(tenant))));

	}

	@Override
	public Optional<Tenant> findById(TenantId tenantId) {
		return Optional.ofNullable(tenantId)
				.map(BaseId::getValue)
				.map(jpaTenantRepository::findById)
				.flatMap(it -> it.map(TenantEntityMapper.INSTANCE::toModel));
	}

	@Override
	public Paging<Tenant> queryTenants(FindTenantQuery query) {
		final var request = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterTenantQuery.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = getTenantPredicate(request);

		final var pageResult = jpaTenantRepository.findAllWithBase(predicate, pageable);
		return new Paging<>(pageResult.getContent()
				.stream()
				.map(TenantEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}

	private static WhereBuilder getTenantPredicate(FilterTenantQuery request) {
		return WhereBuilder.build()
				.applyIf(!CollectionUtils.isEmpty(request.getByTenantIds()), where -> where.and(
						QTenantEntity.tenantEntity.id.in(request.getByTenantIds()
								.stream()
								.map(BaseId::getValue)
								.collect(Collectors.toSet()))))
				.applyIf(request.getByTenantId() != null, where -> where.and(QTenantEntity.tenantEntity.id.eq(request
						.getByTenantId()
						.getValue())))
				.applyIf(request.getByTenantStatuses() != null, where -> where.and(QTenantEntity.tenantEntity.status.in(
						request.getByTenantStatuses())))
				.applyIf(request.getByTitle() != null, where -> where.and(QTenantEntity.tenantEntity.title.eq(request
						.getByTitle())))
				.applyIf(request.getByContainingTitle() != null, where -> where.and(QTenantEntity.tenantEntity.title
						.containsIgnoreCase(request.getByContainingTitle())))
				.applyIf(request.getByDomainHost() != null, where -> where.and(QTenantEntity.tenantEntity.domainHost.eq(
						request.getByDomainHost())))
				.applyIf(request.getByCreatedRange() != null, where -> where.and(QTenantEntity.tenantEntity.createdAt
						.after(request.getByCreatedRange()
								.from()))
						.and(QTenantEntity.tenantEntity.createdAt.before(request.getByCreatedRange()
								.to())))

		;
	}

	@Override
	public boolean existsTenant(FilterTenantQuery query) {
		return jpaTenantRepository.exists(getTenantPredicate(query));//TODO: write method existsTenant

	}
}
