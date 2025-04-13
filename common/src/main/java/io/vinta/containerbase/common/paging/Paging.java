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
package io.vinta.containerbase.common.paging;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Paging<T> {

	private List<T> content;

	private long totalElements;

	private int totalPages;

	private int page;

	private List<String> sort;

	public Paging(List<T> content, long totalElements, int totalPages, int page, List<String> sort) {
		this.content = content;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.page = page;
		this.sort = sort;
	}

	public static <T> Paging<T> empty() {
		return Paging.<T>builder()
				.sort(Collections.emptyList())
				.content(Collections.emptyList())
				.totalElements(0)
				.totalPages(0)
				.page(0)
				.build();
	}

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<String> getSort() {
		return sort;
	}

	public void setSort(List<String> sort) {
		this.sort = sort;
	}

	public <U> Paging<U> map(Function<? super T, ? extends U> converter) {
		return Paging.<U>builder()
				.content(content.stream()
						.map(converter)
						.collect(Collectors.toList()))
				.totalElements(totalElements)
				.totalPages(totalPages)
				.page(page)
				.sort(sort)
				.build();
	}

	public static final class Builder<T> {
		private List<T> content;
		private long totalElements;
		private int totalPages;
		private int page;
		private List<String> sort = Collections.emptyList();

		private Builder() {
		}

		public Builder<T> content(List<T> content) {
			if (content != null && !content.isEmpty()) {
				this.content = content;
			} else {
				this.content = Collections.emptyList();
			}
			return this;
		}

		public Builder<T> totalElements(long totalElements) {
			this.totalElements = totalElements;
			return this;
		}

		public Builder<T> totalPages(int totalPages) {
			this.totalPages = totalPages;
			return this;
		}

		public Builder<T> page(int page) {
			this.page = page;
			return this;
		}

		public Builder<T> sort(List<String> sort) {
			this.sort = sort;
			return this;
		}

		public Paging<T> build() {
			return new Paging<>(content, totalElements, totalPages, page, sort);
		}
	}
}
