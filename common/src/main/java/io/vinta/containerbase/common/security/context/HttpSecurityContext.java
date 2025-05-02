package io.vinta.containerbase.common.security.context;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import lombok.Builder;

public class HttpSecurityContext extends AppSecurityContext {

	@Builder(builderMethodName = "httpSecurityContextBuilder")
	public HttpSecurityContext(final String authorizationToken, final JwtTokenClaim tokenClaim, final TenantId tenantId,
			final UserId userId, final UserType userType) {
		super(authorizationToken, tokenClaim, RequestSecurityContext.HTTP, tenantId, userId, userType);
	}
}
