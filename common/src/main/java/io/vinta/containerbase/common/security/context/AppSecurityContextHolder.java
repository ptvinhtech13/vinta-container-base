package io.vinta.containerbase.common.security.context;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.exceptions.BadRequestException;
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

	public static TenantId getTenantId() {
		return getContext().map(AppSecurityContext::getTenantId)
				.orElseThrow(() -> new BadRequestException("Missing X-Tenant-ID"));
	}

	public static UserId getUserId() {
		return getContext().map(AppSecurityContext::getUserId)
				.orElseThrow(() -> new BadRequestException("Missing UserId"));
	}
}
