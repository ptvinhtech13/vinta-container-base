package io.vinta.containerbase.core.users.request;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
@With
public class DeleteUserCommand {
	private final TenantId tenantId;
	private final Set<UserId> byUserIds;
}
