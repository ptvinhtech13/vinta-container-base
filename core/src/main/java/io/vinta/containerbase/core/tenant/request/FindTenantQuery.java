package io.vinta.containerbase.core.tenant.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindTenantQuery extends PaginationQuery<FilterTenantQuery> {
	@Builder
	public FindTenantQuery(FilterTenantQuery filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindTenantQuery withFilter(FilterTenantQuery newFilter) {
		return new FindTenantQuery(newFilter, size, page, sortDirection, sortFields);
	}
}
