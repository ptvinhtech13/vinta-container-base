package io.vinta.containerbase.data.access.relational.tenant.entities;

import io.vinta.containerbase.common.annotation.SnowflakeId;
import io.vinta.containerbase.common.enums.TenantStatus;
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
@Table(name = "tenants")
public class TenantEntity {

	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "title")
	@Length(max = 128)
	@NotEmpty
	private String title;

	@Column(name = "description")
	@Length(max = 500)
	private String description;

	@Column(name = "domain_host")
	@Length(max = 128)
	@NotEmpty
	private String domainHost;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private TenantStatus status;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;
}
