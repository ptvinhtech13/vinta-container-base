package io.vinta.containerbase.data.access.relational.exportjob.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.vinta.containerbase.common.annotation.SnowflakeId;
import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.core.containers.request.FilterContainer;
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
@Table(name = "export_job")
public class ExportJobEntity {
	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "export_form_id")
	@NotNull
	private String exportFormId;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ExportJobStatus status;

	@Column(name = "total_container")
	private Long totalContainer;

	@Column(name = "total_page")
	private Integer totalPage;

	@Column(name = "total_exported_container")
	private Long totalExportedContainer;

	@Column(name = "last_exported_page")
	private Integer lastExportedPage;

	@Type(JsonType.class)
	@Column(name = "filter_container", columnDefinition = "json")
	private FilterContainer filterContainer;

	@Column(name = "file_output_path")
	private String fileOutputPath;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;
}