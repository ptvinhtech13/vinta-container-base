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
package io.vinta.containerbase.common.dialect;

import io.vinta.containerbase.common.querydsl.ExtendedPostgresFunctions;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * By default, JPQL doesn't support database-specific operators, and PostgresPlusDialect was missing GIST Operators used
 * for Ltree query. In below, customization allows to register and GIST operators used for Ltree like a function.
 * Meaning, all JPQL query needing to use Ltree GIST operator would need to use a "mask function" instead directly.
 * Using masking function approach would auto-convert to GIST clause in statement
 */
@Slf4j
public class ExtendedPostgresDialect extends PostgresPlusDialect {

	public ExtendedPostgresDialect() {
		super();
	}

	/**
	 * This dialect would be injected in spring YAML hibernate configuration. ltree_left_descendant_right({0},{1})
	 * pattern would be converted to ?1 <@ ?2 in statement. For example: JPQL: * SELECT p * FROM PersonEntity p * WHERE
	 * ltree_left_descendant_right(p.groupPath, ?2) SQL: * SELECT p * FROM person p * WHERE p.group_path <@ ?2
	 */
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

	}

}