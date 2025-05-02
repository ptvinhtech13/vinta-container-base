package io.vinta.containerbase.data.access.relational.users.entities;

import io.vinta.containerbase.common.annotation.SnowflakeId;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.data.access.relational.userrole.entities.UserRoleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
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
@Table(name = "users")
public class UserEntity {

	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "user_type")
	@Enumerated(EnumType.STRING)
	@NotNull
	private UserType userType;

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private UserStatus userStatus;

	@Column(name = "email")
	@Length(max = 60)
	@NotEmpty
	private String email;

	@Column(name = "phone_number")
	@Length(max = 20)
	private String phoneNumber;

	@Column(name = "full_name")
	@Length(max = 128)
	private String fullName;

	@Column(name = "avatar_path")
	@Length(max = 256)
	private String avatarPath;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<UserRoleEntity> userRoles = new HashSet<>();

	@CreatedBy
	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}
