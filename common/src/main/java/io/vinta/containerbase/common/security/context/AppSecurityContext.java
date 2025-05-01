package io.vinta.containerbase.common.security.context;

import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AppSecurityContext {

	private String authorizationToken;
	private FullTokenClaim fullTokenClaim;
	private RequestSecurityContext context;
}
