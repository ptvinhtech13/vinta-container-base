package io.vinta.containerbase.common.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryDslJsonbFunctions {

	public static BooleanExpression isLeftEqualsRight(StringPath leftPath, String leftField, String right) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.JSONB_LEFT_EQUALS_RIGHT.getFuncName()
				+ "({0},{1},{2})", leftPath, leftField, right);
	}

	public static BooleanExpression isLeftContainsRight(StringPath leftPath, String leftField, String right) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.JSONB_LEFT_CONTAINS_RIGHT.getFuncName()
				+ "({0},{1},to_jsonb({2}))", leftPath, leftField, right);
	}
}
