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
package io.vinta.containerbase.common.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.util.Streamable;

@NoRepositoryBean
public interface BaseQuerydslRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
	default Optional<T> findOneWithBase(Predicate predicate) {
		return findOne(WhereBuilder.build()
				.and(predicate)
				.and(softDeletionPredicate()));
	}

	default List<T> findAllWithBase(Predicate predicate) {
		return Streamable.of(findAll(WhereBuilder.build()
				.and(predicate)
				.and(softDeletionPredicate())))
				.stream()
				.toList();
	}

	default Page<T> findAllWithBase(Predicate predicate, Pageable pageable) {
		return findAll(WhereBuilder.build()
				.and(predicate)
				.and(softDeletionPredicate()), pageable);
	}

	default Predicate softDeletionPredicate() {
		return Expressions.asBoolean(true)
				.isTrue();
	}
}
