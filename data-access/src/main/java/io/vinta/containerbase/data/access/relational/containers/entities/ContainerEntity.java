package io.vinta.containerbase.data.access.relational.containers.entities;

import io.vinta.containerbase.common.annotation.SnowflakeId;
import io.vinta.containerbase.common.enums.ContainerState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "containers")
public class ContainerEntity {
	@Id
	@SnowflakeId
	private Long id;

	@Column(name = "import_iob_id")
	private String importJobId;

	@Column(name = "container_number")
	private String containerNumber;

	@Column(name = "iso_equipment_code")
	private String isoEquipmentCode;

	@Column(name = "equipment_reference")
	private String equipmentReference;

	@Column(name = "transport_equipment_type")
	private String transportEquipmentType;

	@Column(name = "tare_weight_kg")
	private String tareWeightKg;

	@Column(name = "max_gross_weight_kg")
	private String maxGrossWeightKg;

	@Column(name = "payload_weight_kg")
	private String payloadWeightKg;

	@Column(name = "is_reefer")
	private Boolean isReefer;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private ContainerState state;

	@Column(name = "damage_description")
	private String damageDescription;

	@Column(name = "booking_reference")
	private String bookingReference;

	@Column(name = "shipment_reference")
	private String shipmentReference;

	@Column(name = "contents_description")
	private String contentsDescription;

	@Column(name = "seal_number")
	private String sealNumber;

	@Column(name = "seal_source")
	private String sealSource;

	@Column(name = "owner_shipping_line_code")
	private String ownerShippingLineCode;

	@Column(name = "owner_shipping_scac")
	private String ownerShippingSCAC;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "owner_address")
	private String ownerAddress;

	@CreatedDate
	@Column(name = "created_at")
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Instant updatedAt;

}
