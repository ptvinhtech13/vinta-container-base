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

import com.querydsl.core.types.Operator;
import lombok.Getter;

@Getter
public enum ExtendedPostgresFunctions {
	LTREE_LEFT_ANCESTOR_RIGHT(ExtendedPostgresOperators.LTREE_LEFT_ANCESTOR_RIGHT, "ltree_left_ancestor_right",
			"ltree_left_ancestor_right({0},{1})"),

	LTREE_LEFT_DESCENDANT_RIGHT(ExtendedPostgresOperators.LTREE_LEFT_DESCENDANT_RIGHT, "ltree_left_descendant_right",
			"ltree_left_descendant_right({0},{1})"),

	LTREE_LEFT_CHILDREN_LEVEL(ExtendedPostgresOperators.LTREE_LEFT_CHILDREN_LEVEL, "ltree_left_children_right_level",
			"ltree_left_children_right_level({0},{1})"),

	JSONB_LEFT_EQUALS_RIGHT(ExtendedPostgresOperators.JSONB_LEFT_EQUALS_RIGHT, "jsonb_left_equals_right",
			"jsonb_left_equals_right({0},{1},{2})");

	private final Operator operator;
	private final String funcName;
	private final String pattern;

	ExtendedPostgresFunctions(Operator operator, String funcName, String pattern) {
		this.operator = operator;
		this.funcName = funcName;
		this.pattern = pattern;
	}
}
