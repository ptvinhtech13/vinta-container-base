package io.vinta.containerbase.core.users.request;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.range.InstantDateTimeRange;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@With
@Getter
@Builder
@RequiredArgsConstructor
public class FilterUserQuery {
	private final UserId byUserId;
	private final TenantId byTenantId;
	private final Set<RoleId> byRoleIds;
	private final Set<UserType> byUserTypes;
	private final Set<UserStatus> byUserStatuses;
	private final Set<UserId> byUserIds;
	private final String byPhoneNumber;
	private final String byEmail;
	private final String byContainingEmail;
	private final String byName;
	private final String byRealmId;
	private final InstantDateTimeRange byCreatedRange;
}
