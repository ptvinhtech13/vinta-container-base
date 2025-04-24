package io.vinta.containerbase.data.access.relational.importjob.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import io.vinta.containerbase.core.importjob.entities.ImportJobTrackingMetrics;
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
@Table(name = "import_jobs")
public class ImportJobEntity {

	@Id
	private String id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ImportJobStatus status;

	@Column(name = "file_form_id")
	@NotEmpty
	private String fileFormId;

	@Column(name = "uploaded_file_path")
	@NotEmpty
	private String uploadedFilePath;

	@Type(JsonType.class)
	@Column(name = "actual_schema", columnDefinition = "json")
	private FileFormSchema actualSchema;

	@Type(JsonType.class)
	@Column(name = "metrics", columnDefinition = "json")
	private ImportJobTrackingMetrics metrics;

	@Column(name = "consolidated_error_messages")
	private String consolidatedErrorMessages;

	@Column(name = "remark")
	private String remark;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}
