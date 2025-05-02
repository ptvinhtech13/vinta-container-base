/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.data.access.relational.tenant.repository;

import com.querydsl.core.types.Predicate;
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
import io.vinta.containerbase.data.access.relational.tenant.entities.TenantEntity;
import io.vinta.containerbase.data.access.relational.tenant.mapper.TenantEntityMapper;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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
                .orElseGet(() -> TenantEntityMapper.INSTANCE.toModel(jpaTenantRepository.save(TenantEntityMapper.INSTANCE
                        .toCreate(tenant))));
    }

    @Override
    public Optional<Tenant> findById(TenantId tenantId) {
        return jpaTenantRepository.findById(tenantId.getValue())
                .map(TenantEntityMapper.INSTANCE::toModel);
    }

    @Override
    public Paging<Tenant> queryTenants(FindTenantQuery query) {
        final var filter = Optional.ofNullable(query.getFilter())
                .orElseGet(() -> FilterTenantQuery.builder()
                        .build());

        final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
                .getSortDirection()), query.getSortFields()
                        .toArray(String[]::new));

        final var predicate = buildTenantPredicate(filter);
        final var page = jpaTenantRepository.findAll(predicate, pageable);

        return Paging.<Tenant>builder()
                .content(page.getContent()
                        .stream()
                        .map(TenantEntityMapper.INSTANCE::toModel)
                        .collect(Collectors.toList()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    public boolean existsTenant(FilterTenantQuery query) {
        return jpaTenantRepository.exists(buildTenantPredicate(query));
    }

    private Predicate buildTenantPredicate(FilterTenantQuery query) {
        final var queryFilter = Optional.ofNullable(query)
                .orElseGet(() -> FilterTenantQuery.builder()
                        .build());
        return WhereBuilder.build()
                .applyIf(queryFilter.getByTenantId() != null, where -> where.and(QTenantEntity.tenantEntity.id.eq(queryFilter
                        .getByTenantId()
                        .getValue())))
                .applyIf(queryFilter.getByTenantIds() != null, where -> where.and(QTenantEntity.tenantEntity.id.in(queryFilter
                        .getByTenantIds()
                        .stream()
                        .map(BaseId::getValue)
                        .collect(Collectors.toSet()))))
                .applyIf(queryFilter.getByTenantStatuses() != null, where -> where.and(QTenantEntity.tenantEntity.status
                        .in(queryFilter.getByTenantStatuses())))
                .applyIf(queryFilter.getByTitle() != null, where -> where.and(QTenantEntity.tenantEntity.title.eq(
                        queryFilter.getByTitle())))
                .applyIf(queryFilter.getByContainingTitle() != null, where -> where.and(QTenantEntity.tenantEntity.title
                        .containsIgnoreCase(queryFilter.getByContainingTitle())))
                .applyIf(queryFilter.getByDomainHost() != null, where -> where.and(QTenantEntity.tenantEntity.domainHost.eq(
                        queryFilter.getByDomainHost())))
                .applyIf(queryFilter.getByCreatedRange() != null, where -> where.and(QTenantEntity.tenantEntity.createdAt
                        .after(queryFilter.getByCreatedRange()
                                .from()))
                        .and(QTenantEntity.tenantEntity.createdAt.before(queryFilter.getByCreatedRange()
                                .to())))
                .build();
    }
}
