package io.vinta.containerbase.common.security.constants;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class SecurityConstants {
	public static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
	public static final String BEARER = "Bearer";
	public static final String X_TENANT_ID = "X-Tenant-ID";
}
