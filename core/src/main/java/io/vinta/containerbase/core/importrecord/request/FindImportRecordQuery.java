package io.vinta.containerbase.core.importrecord.request;

import io.vinta.containerbase.common.paging.PaginationQuery;
import java.util.Collection;
import lombok.Builder;

public class FindImportRecordQuery extends PaginationQuery<FilterImportRecord> {
	@Builder
	public FindImportRecordQuery(FilterImportRecord filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		super(filter, size, page, sortDirection, sortFields);
	}

	public FindImportRecordQuery withFilter(FilterImportRecord newFilter) {
		return new FindImportRecordQuery(newFilter, size, page, sortDirection, sortFields);
	}

}
