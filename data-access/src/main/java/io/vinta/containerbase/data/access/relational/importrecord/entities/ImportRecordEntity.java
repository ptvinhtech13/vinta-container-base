package io.vinta.containerbase.data.access.relational.importrecord.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.vinta.containerbase.common.annotation.SnowflakeId;
import io.vinta.containerbase.common.enums.ImportRecordStatus;
import io.vinta.containerbase.common.enums.ImportRecordType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;
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
@Table(name = "import_records")
public class ImportRecordEntity {
	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "import_job_id")
	@NotNull
	private String importJobId;

	@Column(name = "record_index")
	@NotNull
	private Long recordIndex;

	@Column(name = "record_type")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ImportRecordType recordType;

	@Column(name = "record_status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ImportRecordStatus recordStatus;

	@Column(name = "error_message")
	private String errorMessage;

	@Column(name = "stacktrace")
	private String stacktrace;

	@Type(JsonType.class)
	@Column(name = "data_values", columnDefinition = "json")
	private Map<String, String> data;

	@Column(name = "raw_data_values")
	@NotNull
	private String rawData;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}
