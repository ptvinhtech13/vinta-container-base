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
