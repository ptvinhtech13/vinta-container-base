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
package io.vinta.containerbase.data.access.relational.containers.repository;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.containers.ContainerRepository;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import io.vinta.containerbase.data.access.relational.containers.mapper.ContainerEntityMapper;
import io.vinta.containerbase.data.access.relational.importjob.entities.QImportJobEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContainerRepositoryImpl implements ContainerRepository {

	private final JpaContainerRepository repository;

	@Override
	public Paging<Container> queryContainers(FindContainerQuery query) {
		final var filter = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterContainer.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build()
				.applyIf(filter.getByCreatedFrom() != null, where -> where.and(
						QImportJobEntity.importJobEntity.createdAt.after(filter.getByCreatedFrom())))
				.applyIf(filter.getByCreatedTo() != null, where -> where.and(QImportJobEntity.importJobEntity.createdAt
						.after(filter.getByCreatedTo())));

		final var pageResult = repository.findAllWithBase(predicate, pageable);

		return new Paging<>(pageResult.getContent()
				.stream()
				.map(ContainerEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}
}
