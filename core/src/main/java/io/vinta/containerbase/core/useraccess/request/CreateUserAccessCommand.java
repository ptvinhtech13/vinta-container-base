package io.vinta.containerbase.core.useraccess.request;

import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.core.useraccess.entities.UserAccessData;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
@With
public class CreateUserAccessCommand {
	private final UserId userId;
	private final UserAccessType accessType;
	private final UserAccessData accessData;
}