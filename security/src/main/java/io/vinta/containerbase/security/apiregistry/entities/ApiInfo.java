/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
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
