package io.vinta.containerbase.core.userrole.request;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
@With
public class CreateUserRoleCommand {
	private final UserId userId;
	private final TenantId tenantId;
	private final RoleId roleId;
}