package io.vinta.containerbase.data.access.relational.userrole.entities;

import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleId implements Serializable {
	private UserEntity user;
	private Long tenantId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserRoleId that = (UserRoleId) o;

		return new EqualsBuilder().append(user.getId(), that.user.getId())
				.append(tenantId, that.tenantId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(user.getId())
				.append(tenantId)
				.toHashCode();
	}
}
