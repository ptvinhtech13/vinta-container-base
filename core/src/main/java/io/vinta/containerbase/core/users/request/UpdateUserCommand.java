package io.vinta.containerbase.core.users.request;

import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateUserCommand {
	private final UserId userId;
	private final TenantId tenantId;
	private final String email;
	private final String fullName;
	private final String avatarPath;
	private final String phoneNumber;
	private final UserStatus userStatus;
}
