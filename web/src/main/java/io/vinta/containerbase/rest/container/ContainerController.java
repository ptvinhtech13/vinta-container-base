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
package io.vinta.containerbase.rest.container;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import io.vinta.containerbase.rest.api.ContainerApi;
import io.vinta.containerbase.rest.container.mapper.ContainerPaginationResponseMapper;
import io.vinta.containerbase.rest.container.response.ContainerResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContainerController implements ContainerApi {

	private final ContainerQueryService queryService;

	@Override
	public Paging<ContainerResponse> queryContainer(ZonedDateTime dateFrom, ZonedDateTime dateTo, int page) {
		final var result = queryService.queryContainers(FindContainerQuery.builder()
				.filter(FilterContainer.builder()
						.byCreatedFrom(Optional.ofNullable(dateFrom)
								.map(ZonedDateTime::toInstant)
								.orElse(null))
						.byCreatedTo(Optional.ofNullable(dateTo)
								.map(ZonedDateTime::toInstant)
								.orElse(null))
						.build())
				.page(page - 1)
				.sortFields(List.of("createdAt"))
				.sortDirection("DESC")
				.size(20)
				.build());

		return ContainerPaginationResponseMapper.INSTANCE.toResponse(result);

	}
}
