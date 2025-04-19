package io.vinta.containerbase.common.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryDslLtreeFunctions {

	public static BooleanExpression isLeftAccessorRight(StringPath leftPath, String right) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.LTREE_LEFT_ANCESTOR_RIGHT.getFuncName()
				+ "({0},text2ltree({1}))", leftPath, right);
	}

	public static BooleanExpression isLeftDescendantRight(StringPath leftPath, String right) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.LTREE_LEFT_DESCENDANT_RIGHT.getFuncName()
				+ "({0},text2ltree({1}))", leftPath, right);
	}

	public static BooleanExpression leftChildrenInLevelRight(StringPath leftPath, String byUserGroupPath,
			Integer level) {
		return Expressions.booleanTemplate(ExtendedPostgresFunctions.LTREE_LEFT_CHILDREN_LEVEL.getFuncName()
				+ "({0},lquery({1}))", leftPath, byUserGroupPath + ".*{LEVEL_HOLDER}".replace("LEVEL_HOLDER", level
						.toString()));

	}
}
