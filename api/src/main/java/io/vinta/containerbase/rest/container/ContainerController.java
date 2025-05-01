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
