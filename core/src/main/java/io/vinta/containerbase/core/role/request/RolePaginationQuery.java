package io.vinta.containerbase.core.role.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class RolePaginationQuery extends PaginationQuery<FilterRoleQuery> {
	@Builder
	public RolePaginationQuery(FilterRoleQuery filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public RolePaginationQuery withFilter(FilterRoleQuery newRequest) {
		return new RolePaginationQuery(newRequest, size, page, sortDirection, sortFields);
	}
}
