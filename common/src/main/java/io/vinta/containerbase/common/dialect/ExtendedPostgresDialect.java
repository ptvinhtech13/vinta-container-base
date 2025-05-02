package io.vinta.containerbase.common.dialect;

import io.vinta.containerbase.common.querydsl.ExtendedPostgresFunctions;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.type.StandardBasicTypes;

@Slf4j
public class ExtendedPostgresDialect extends PostgresPlusDialect {

	public ExtendedPostgresDialect() {
		super();
	}

	@Override
	public void initializeFunctionRegistry(FunctionContributions functionContributions) {
		super.initializeFunctionRegistry(functionContributions);
		final var registry = functionContributions.getFunctionRegistry();
		final var types = functionContributions.getTypeConfiguration()
				.getBasicTypeRegistry();

		registry.registerPattern(ExtendedPostgresFunctions.LTREE_LEFT_DESCENDANT_RIGHT.getFuncName(), "?1 <@ ?2", types
				.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.LTREE_LEFT_ANCESTOR_RIGHT.getFuncName(), "?1 @> ?2", types
				.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.LTREE_LEFT_CHILDREN_LEVEL.getFuncName(), "?1 ~ ?2", types
				.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.JSONB_LEFT_EQUALS_RIGHT.getFuncName(), "?1 ->> ?2 = ?3",
				types.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.VARCHAR_ARRAY_LEFT_ANY_CONTAINS_RIGHT.getFuncName(),
				"?1 @> string_to_array(?2, ',')::varchar[]", types.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT.getFuncName(), "?1 ilike ?2", types
				.resolve(StandardBasicTypes.BOOLEAN));

		registry.registerPattern(ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER
				.getFuncName(), "?1 ilike ?2 escape '!'", types.resolve(StandardBasicTypes.BOOLEAN));

	}

}