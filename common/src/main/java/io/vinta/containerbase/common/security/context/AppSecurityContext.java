package io.vinta.containerbase.common.security.context;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AppSecurityContext {

	private String authorizationToken;
	private FullTokenClaim fullTokenClaim;
	private RequestSecurityContext context;
	private TenantId tenantId;
	private UserId userId;
	private UserType userType;
}
