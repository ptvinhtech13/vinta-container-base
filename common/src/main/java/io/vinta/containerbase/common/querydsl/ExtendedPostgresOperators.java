package io.vinta.containerbase.common.querydsl;

import com.querydsl.core.types.Operator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtendedPostgresOperators {
	public static final Operator LTREE_LEFT_DESCENDANT_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.LTREE_LEFT_DESCENDANT_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator LTREE_LEFT_ANCESTOR_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.LTREE_LEFT_ANCESTOR_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator LTREE_LEFT_CHILDREN_LEVEL = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.LTREE_LEFT_CHILDREN_LEVEL.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator JSONB_LEFT_EQUALS_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.JSONB_LEFT_EQUALS_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator JSONB_LEFT_CONTAINS_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.JSONB_LEFT_CONTAINS_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator VARCHAR_ARRAY_LEFT_ANY_CONTAINS_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.VARCHAR_ARRAY_LEFT_ANY_CONTAINS_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT_ESCAPE_SPECIAL_CHARACTER.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

	public static final Operator ILIKE_LEFT_MATCHING_RIGHT = new Operator() {
		@Override
		public String name() {
			return ExtendedPostgresFunctions.ILIKE_LEFT_MATCHING_RIGHT.name();
		}

		@Override
		public Class<?> getType() {
			return Boolean.class;
		}
	};

}