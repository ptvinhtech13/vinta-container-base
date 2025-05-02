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
			"jsonb_left_equals_right({0},{1},{2})"),

	VARCHAR_ARRAY_LEFT_ANY_CONTAINS_RIGHT(ExtendedPostgresOperators.VARCHAR_ARRAY_LEFT_ANY_CONTAINS_RIGHT,
			"varchar_array_left_contains_any_right", "varchar_array_left_contains_any_right({0},{1})"),

	ILIKE_LEFT_MATCHING_RIGHT(ExtendedPostgresOperators.ILIKE_LEFT_MATCHING_RIGHT, "ilike_left_matches_right",
			"ilike_left_matches_right({0},{1})"),

	ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER(
			ExtendedPostgresOperators.ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER,
			"ilike_left_matches_right_escape_special_character",
			"ilike_left_matches_right_escape_special_character({0},{1})"),;

	private final Operator operator;
	private final String funcName;
	private final String pattern;

	ExtendedPostgresFunctions(Operator operator, String funcName, String pattern) {
		this.operator = operator;
		this.funcName = funcName;
		this.pattern = pattern;
	}
}