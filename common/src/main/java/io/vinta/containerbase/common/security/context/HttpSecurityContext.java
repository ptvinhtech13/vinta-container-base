package io.vinta.containerbase.common.security.context;

import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import lombok.Builder;

public class HttpSecurityContext extends AppSecurityContext {

	@Builder(builderMethodName = "httpSecurityContextBuilder")
	public HttpSecurityContext(final String authorizationToken, final FullTokenClaim fullTokenClaim) {
		super(authorizationToken, fullTokenClaim, RequestSecurityContext.HTTP);
	}
}
