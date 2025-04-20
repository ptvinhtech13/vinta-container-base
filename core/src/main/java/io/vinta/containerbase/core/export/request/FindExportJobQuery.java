package io.vinta.containerbase.core.export.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindExportJobQuery extends PaginationQuery<FilterExportJob> {
	@Builder
	public FindExportJobQuery(FilterExportJob filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindExportJobQuery withFilter(FilterExportJob newFilter) {
		return new FindExportJobQuery(newFilter, size, page, sortDirection, sortFields);
	}

}
