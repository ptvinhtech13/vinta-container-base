package io.vinta.containerbase.core.dashboard.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindDashboardQuery extends PaginationQuery<FilterDashboardQuery> {
	@Builder
	public FindDashboardQuery(FilterDashboardQuery filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindDashboardQuery withFilter(FilterDashboardQuery newFilter) {
		return new FindDashboardQuery(newFilter, size, page, sortDirection, sortFields);
	}
}
