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