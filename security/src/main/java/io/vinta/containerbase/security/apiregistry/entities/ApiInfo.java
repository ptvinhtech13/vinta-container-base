package io.vinta.containerbase.security.apiregistry.entities;

import io.vinta.containerbase.common.security.permissions.PacificApiPermissionKey;
import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.HttpMethod;

@Builder
@RequiredArgsConstructor
@With
@Getter
public class ApiInfo {
	private final String serviceId;
	private final String path;
	private final HttpMethod method;
	private final PlatformApiSecurityLevel securityLevel;
	private final Set<PacificApiPermissionKey> permissionKeys;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ApiInfo that = (ApiInfo) o;
		return Objects.equals(serviceId, that.serviceId) && Objects.equals(path, that.path) && Objects.equals(method,
				that.method);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serviceId, path, method);
	}
}
