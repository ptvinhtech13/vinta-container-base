package io.vinta.containerbase.core.users.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class UserPaginationQuery extends PaginationQuery<FilterUserQuery> {
	@Builder
	public UserPaginationQuery(FilterUserQuery filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public UserPaginationQuery withFilter(FilterUserQuery newRequest) {
		return new UserPaginationQuery(newRequest, size, page, sortDirection, sortFields);
	}
}
