package io.vinta.containerbase.data.access.relational.role.entities;

import io.hypersistence.utils.hibernate.type.array.LongArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import io.vinta.containerbase.common.annotation.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
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
@Table(name = "roles")
public class RoleEntity {

	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "title")
	@NotEmpty
	@Length(max = 128)
	private String title;

	@Column(name = "tenant_id")
	@NotNull
	private Long tenantId;

	@Column(name = "description")
	@Length(max = 256)
	private String description;

	@Column(name = "feature_node_ids", columnDefinition = "bigint[]")
	@Enumerated(EnumType.STRING)
	@Type(value = LongArrayType.class, parameters = @Parameter(name = AbstractArrayType.SQL_ARRAY_TYPE, value = "bigint"))
	private Long[] featureNodeIds;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}