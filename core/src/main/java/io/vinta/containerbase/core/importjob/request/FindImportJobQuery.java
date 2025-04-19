package io.vinta.containerbase.core.importjob.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindImportJobQuery extends PaginationQuery<FilterImportJob> {
	@Builder
	public FindImportJobQuery(FilterImportJob filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindImportJobQuery withFilter(FilterImportJob newFilter) {
		return new FindImportJobQuery(newFilter, size, page, sortDirection, sortFields);
	}

}
