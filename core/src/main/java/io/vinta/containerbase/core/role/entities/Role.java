package io.vinta.containerbase.core.role.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.RoleId;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
public class Role extends BaseEntity<RoleId> {
	private final RoleId id;
	//TODO: Vinh implements Role.java
}