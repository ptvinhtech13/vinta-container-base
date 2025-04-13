package io.vinta.containerbase.common.idgenerator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnowflakeIdGenerator {

	private static final Snowflake GENERATOR = new Snowflake();

	public static Long randomId() {
		return GENERATOR.nextId();
	}
}
