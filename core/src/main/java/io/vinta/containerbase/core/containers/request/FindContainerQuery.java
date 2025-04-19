package io.vinta.containerbase.core.containers.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindContainerQuery extends PaginationQuery<FilterContainer> {
	@Builder
	public FindContainerQuery(FilterContainer filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindContainerQuery withFilter(FilterContainer newFilter) {
		return new FindContainerQuery(newFilter, size, page, sortDirection, sortFields);
	}

}
