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
package io.vinta.containerbase.data.access.relational.dashboard.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.DashboardId;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.ExtendedPostgresFunctions;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.dashboard.DashboardRepository;
import io.vinta.containerbase.core.dashboard.entities.Dashboard;
import io.vinta.containerbase.core.dashboard.request.FilterDashboardQuery;
import io.vinta.containerbase.core.dashboard.request.FindDashboardQuery;
import io.vinta.containerbase.data.access.relational.dashboard.entities.DashboardAccessDataPolicy;
import io.vinta.containerbase.data.access.relational.dashboard.entities.QDashboardEntity;
import io.vinta.containerbase.data.access.relational.dashboard.mapper.DashboardEntityMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository {

	private final JpaDashboardRepository jpaDashboardRepository;

	@Override
	public Dashboard save(Dashboard dashboard) {
		return Optional.ofNullable(dashboard.getId())
				.map(BaseId::getValue)
				.map(jpaDashboardRepository::findById)
				.map(it -> it.orElseThrow(() -> new NotFoundException("Dashboard not found")))
				.map(existing -> DashboardEntityMapper.INSTANCE.toUpdate(existing, dashboard))
				.map(jpaDashboardRepository::save)
				.map(DashboardEntityMapper.INSTANCE::toModel)
				.orElseGet(() -> DashboardEntityMapper.INSTANCE.toModel(jpaDashboardRepository.save(
						DashboardEntityMapper.INSTANCE.toCreate(dashboard))));
	}

	@Override
	public void deleteDashboard(DashboardId dashboardId) {
		jpaDashboardRepository.deleteById(dashboardId.getValue());
	}

	@Override
	public Paging<Dashboard> queryDashboard(FindDashboardQuery query) {
		final var request = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterDashboardQuery.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = buildDashboardPredicate(request);

		final var pageResult = jpaDashboardRepository.findAllWithBase(predicate, pageable);
		return new Paging<>(pageResult.getContent()
				.stream()
				.map(DashboardEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());
	}

	private Predicate buildDashboardPredicate(FilterDashboardQuery query) {
		final var queryFilter = Optional.ofNullable(query)
				.orElseGet(() -> FilterDashboardQuery.builder()
						.build());
		return WhereBuilder.build()
				.applyIf(queryFilter.getByDashboardType() != null, where -> where.and(
						QDashboardEntity.dashboardEntity.dashboardType.eq(queryFilter.getByDashboardType())))
				.applyIf(!CollectionUtils.isEmpty(queryFilter.getByTenantIds()), where -> where.and(

						Expressions.booleanTemplate(ExtendedPostgresFunctions.JSONB_LEFT_CONTAINS_RIGHT.getFuncName()
								+ "({0},{1},to_jsonb({2}))", QDashboardEntity.dashboardEntity.accessPolicy,
								DashboardAccessDataPolicy.FIELD_NAME_ALLOWED_TENANT_IDS, queryFilter.getByTenantIds()
										.stream()
										.map(BaseId::getValue)
										.toArray(Long[]::new))))
				.and(jpaDashboardRepository.softDeletionPredicate());
	}

	@Override
	public Optional<Dashboard> findOneByDashboardId(DashboardId dashboardId) {
		return jpaDashboardRepository.findById(dashboardId.getValue())
				.map(DashboardEntityMapper.INSTANCE::toModel);
	}
}
