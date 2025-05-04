package io.vinta.containerbase.data.access.relational.userrole.entities;

import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@IdClass(UserRoleId.class)
@Table(name = "user_roles")
public class UserRoleEntity {

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@NotNull
	private UserEntity user;

	@Id
	@Column(name = "tenant_id")
	@NotNull
	private Long tenantId;

	@Column(name = "role_id")
	@NotNull
	private Long roleId;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserRoleEntity that = (UserRoleEntity) o;

		return new EqualsBuilder().append(user.getId(), that.user.getId())
				.append(tenantId, that.tenantId)
				.append(roleId, that.roleId)
				.append(createdAt, that.createdAt)
				.append(updatedAt, that.updatedAt)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(user.getId())
				.append(tenantId)
				.append(roleId)
				.append(createdAt)
				.append(updatedAt)
				.toHashCode();
	}
}
