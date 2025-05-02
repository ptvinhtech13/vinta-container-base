package io.vinta.containerbase.core.useraccess.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserAccessType;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class UserAccess extends BaseEntity<UserId> {
	private final UserId userId;
	private final UserAccessType accessType;
	private final UserAccessData accessData;

	private final Instant createdAt;
	private final Instant updatedAt;
}