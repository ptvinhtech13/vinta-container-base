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
