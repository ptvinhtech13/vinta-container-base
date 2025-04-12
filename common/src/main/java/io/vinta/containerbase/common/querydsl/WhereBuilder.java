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

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import java.util.function.UnaryOperator;

public class WhereBuilder implements Predicate {

	private final BooleanBuilder delegate;

	public static WhereBuilder build() {
		return new WhereBuilder();
	}

	private WhereBuilder() {
		this.delegate = new BooleanBuilder();
	}

	private WhereBuilder(Predicate pPredicate) {
		this.delegate = new BooleanBuilder(pPredicate);
	}

	public WhereBuilder and(Predicate right) {
		return new WhereBuilder(delegate.and(right));
	}

	public WhereBuilder or(Predicate right) {
		return new WhereBuilder(delegate.or(right));
	}

	public WhereBuilder applyIf(boolean condition, UnaryOperator<WhereBuilder> lazyBuilder) {
		return condition ? lazyBuilder.apply(this) : this;
	}

	@Override
	public Predicate not() {
		return delegate.not();
	}

	@Override
	public <R, C> R accept(Visitor<R, C> v, C context) {
		return delegate.accept(v, context);
	}

	@Override
	public Class<? extends Boolean> getType() {
		return delegate.getType();
	}

}