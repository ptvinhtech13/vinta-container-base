package io.vinta.containerbase.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@IdGeneratorType(SnowflakeIdentifierGenerator.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface SnowflakeId {
	String name() default "snowflake_id_generator";
}
