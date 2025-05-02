package io.vinta.containerbase.data.access.relational.userrole.entities;

import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {
	private UserEntity user;
	private Long tenantId;
}
