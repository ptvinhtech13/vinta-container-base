package io.vinta.containerbase.common.security.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainerBaseApiAuthorized {

	PlatformApiSecurityLevel security() default PlatformApiSecurityLevel.AUTHENTICATED;

	ApiPermissionKey[] permissions() default {};
}