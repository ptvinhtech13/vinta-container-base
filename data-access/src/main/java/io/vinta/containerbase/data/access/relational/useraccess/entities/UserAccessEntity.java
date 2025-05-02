package io.vinta.containerbase.data.access.relational.useraccess.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.vinta.containerbase.common.enums.UserAccessType;
import io.vinta.containerbase.core.useraccess.entities.UserAccessData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
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
@EqualsAndHashCode
@Table(name = "user_access")
public class UserAccessEntity {

	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "access_type")
	@Enumerated(EnumType.STRING)
	@NotNull
	private UserAccessType accessType;

	@Type(JsonType.class)
	@Column(name = "access_data", columnDefinition = "json")
	@NotNull
	private UserAccessData accessData;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}
