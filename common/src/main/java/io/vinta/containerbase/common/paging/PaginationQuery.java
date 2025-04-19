package io.vinta.containerbase.common.paging;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public abstract class PaginationQuery<T> {
	private static final int DEFAULT_MAX_SIZE = 500;
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int DEFAULT_PAGE = 0;
	private static final Set<String> DEFAULT_SORT_COLUMNS = Set.of("id");
	private static final String DEFAULT_SORT_DIRECTION = "ASC";

	protected final T filter;
	protected final Integer size;
	protected final Integer page;
	protected final String sortDirection;
	protected final Collection<String> sortFields;

	protected PaginationQuery(Integer size, Integer page, String sortDirection, Collection<String> sortBy) {
		this(null, size, page, sortDirection, sortBy);
	}

	protected PaginationQuery(T filter, PaginationQuery<?> another) {
		this(filter, another.size, another.page, another.sortDirection, another.sortFields);
	}

	protected PaginationQuery(T filter, Integer size, Integer page, String sortDirection,
			Collection<String> sortFields) {
		this.filter = filter;
		this.size = size;
		this.page = page;
		this.sortDirection = sortDirection;
		this.sortFields = sortFields;
	}

	protected PaginationQuery(T filter, Integer size, Integer page) {
		this.filter = filter;
		this.size = size;
		this.page = page;
		this.sortDirection = DEFAULT_SORT_DIRECTION;
		this.sortFields = DEFAULT_SORT_COLUMNS;
	}

	protected int getDefaultMaxSize() {
		return DEFAULT_MAX_SIZE;
	}

	protected int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

	protected int getDefaultPage() {
		return DEFAULT_PAGE;
	}

	protected Set<String> getDefaultSortColumn() {
		return DEFAULT_SORT_COLUMNS;
	}

	protected String getDefaultSortDirection() {
		return DEFAULT_SORT_DIRECTION;
	}

	public T getFilter() {
		return filter;
	}

	public int getSize() {
		return Optional.ofNullable(size)
				.filter(it -> it > 0)
				.filter(it -> it <= getDefaultMaxSize())
				.orElse(getDefaultPageSize());
	}

	public int getPage() {
		return Optional.ofNullable(page)
				.filter(it -> it > -1)
				.orElse(getDefaultPage());
	}

	public String getSortDirection() {
		return Optional.ofNullable(sortDirection)
				.map(it -> "ASC".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC")
				.orElse(DEFAULT_SORT_DIRECTION);
	}

	public Set<String> getSortFields() {
		return Optional.ofNullable(sortFields)
				.filter(collection -> !CollectionUtils.isEmpty(collection))
				.map(Set::copyOf)
				.orElseGet(this::getDefaultSortColumn);
	}
}
