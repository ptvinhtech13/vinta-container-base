package io.vinta.containerbase.core.role.request;

import io.vinta.containerbase.common.baseid.RoleId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateRoleCommand {
	private final RoleId id;
	//TODO: Vinh implements CreateRoleCommand.java
}