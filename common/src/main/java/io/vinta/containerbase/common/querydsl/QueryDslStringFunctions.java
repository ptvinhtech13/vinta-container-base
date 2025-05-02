package io.vinta.containerbase.common.querydsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryDslStringFunctions {

	public static Predicate ilikeLeftMatchRightEscapeSpecialCharacter(StringPath stringPath, String pattern) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER
				.getFuncName() + "({0}, {1})", stringPath, pattern);
	}

	public static Predicate containsWithIgnoreCase(StringPath stringPath, String pattern) {
		return ilikeLeftMatchRight(stringPath, "%" + pattern
				+ "%");
	}

	public static Predicate ilikeLeftMatchRight(StringPath stringPath, String pattern) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT.getFuncName()
				+ "({0}, {1})", stringPath, pattern);
	}
}
