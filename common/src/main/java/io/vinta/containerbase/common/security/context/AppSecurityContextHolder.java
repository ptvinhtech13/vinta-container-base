package io.vinta.containerbase.common.security.context;

import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppSecurityContextHolder {

	private static final ThreadLocal<AppSecurityContext> contextHolder = new ThreadLocal<>();

	public static void clearContext() {
		contextHolder.remove();
	}

	public static Optional<AppSecurityContext> getContext() {
		return Optional.ofNullable(contextHolder.get());
	}

	public static void setContext(final AppSecurityContext context) {
		clearContext();
		contextHolder.set(context);
	}
}
