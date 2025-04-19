package io.vinta.containerbase.common.querydsl;

import com.querydsl.jpa.HQLTemplates;
import java.util.Arrays;

public class ExtendedPostgresTemplates extends HQLTemplates {

	public static final ExtendedPostgresTemplates DEFAULT = new ExtendedPostgresTemplates();

	public ExtendedPostgresTemplates() {
		super();
		Arrays.stream(ExtendedPostgresFunctions.values())
				.forEach(function -> add(function.getOperator(), function.getPattern()));
	}
}