package io.vinta.containerbase.common.security.permissions;

import io.vinta.containerbase.common.security.domains.JwtTokenType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainerBaseApiAuthorized {

	PlatformApiSecurityLevel security() default PlatformApiSecurityLevel.AUTHENTICATED;

	JwtTokenType[] allowedTokenTypes() default { JwtTokenType.ACCESS_TOKEN };

	ApiPermissionKey[] permissions() default {};
}