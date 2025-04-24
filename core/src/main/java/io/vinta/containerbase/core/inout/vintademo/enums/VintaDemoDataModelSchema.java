package io.vinta.containerbase.core.inout.vintademo.enums;

import lombok.Getter;

@Getter
public enum VintaDemoDataModelSchema {
	CONTAINER_NUMBER("containerNumber"),
	ISO_EQUIPMENT_CODE("isoEquipmentCode"),
	EQUIPMENT_REFERENCE("equipmentReference"),
	TRANSPORT_EQUIPMENT_TYPE("transportEquipmentType"),
	TARE_WEIGHT_KG("tareWeightKg"),
	MAX_GROSS_WEIGHT_KG("maxGrossWeightKg"),
	PAYLOAD_WEIGHT_KG("payloadWeightKg"),
	STATE("state"),
	DAMAGE_DESCRIPTION("damageDescription"),
	BOOKING_REFERENCE("bookingReference"),
	SHIPMENT_REFERENCE("shipmentReference"),
	CONTENTS_DESCRIPTION("contentsDescription"),
	SEAL_NUMBER("sealNumber"),
	SEAL_SOURCE("sealSource"),
	OWNER_SHIPPING_LINE_CODE("ownerShippingLineCode"),
	OWNER_SHIPPING_SCAC("ownerShippingSCAC"),
	OWNER_NAME("ownerName"),
	OWNER_ADDRESS("ownerAddress");

	private final String headerKey;

	VintaDemoDataModelSchema(String headerKey) {
		this.headerKey = headerKey;

	}
}
