package io.vinta.containerbase.core.inout.mapper;

import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ContainerFileFormSelector {
	CONTAINER_NUMBER("containerNumber", Container::getContainerNumber),
	ISO_EQUIPMENT_CODE("isoEquipmentCode", Container::getIsoEquipmentCode),
	EQUIPMENT_REFERENCE("equipmentReference", Container::getEquipmentReference),
	TRANSPORT_EQUIPMENT_TYPE("transportEquipmentType", container -> Optional.ofNullable(container
			.getTransportEquipmentType())
			.map(Enum::name)
			.orElse(null)),
	TARE_WEIGHT_KG("tareWeightKg", container -> Optional.ofNullable(container.getTareWeightKg())
			.map(Object::toString)
			.orElse(null)),
	MAX_GROSS_WEIGHT_KG("maxGrossWeightKg", container -> Optional.ofNullable(container.getMaxGrossWeightKg())
			.map(Object::toString)
			.orElse(null)),
	PAYLOAD_WEIGHT_KG("payloadWeightKg", container ->

	Optional.ofNullable(container.getPayloadWeightKg())
			.map(Object::toString)
			.orElse(null)),
	STATE("state", container -> Optional.ofNullable(container.getState())
			.map(Enum::name)
			.orElse(null)),
	DAMAGE_DESCRIPTION("damageDescription", Container::getDamageDescription),
	BOOKING_REFERENCE("bookingReference", Container::getBookingReference),
	SHIPMENT_REFERENCE("shipmentReference", Container::getShipmentReference),
	CONTENTS_DESCRIPTION("contentsDescription", Container::getContentsDescription),
	SEAL_NUMBER("sealNumber", Container::getSealNumber),
	SEAL_SOURCE("sealSource", Container::getSealSource),
	OWNER_SHIPPING_LINE_CODE("ownerShippingLineCode", Container::getOwnerShippingLineCode),
	OWNER_SHIPPING_SCAC("ownerShippingSCAC", Container::getOwnerShippingSCAC),
	OWNER_NAME("ownerName", Container::getOwnerName),
	OWNER_ADDRESS("ownerAddress", Container::getOwnerAddress),;

	private final static Map<String, ContainerFileFormSelector> VALUE_MAP;
	static {
		VALUE_MAP = Stream.of(ContainerFileFormSelector.values())
				.collect(Collectors.toMap(ContainerFileFormSelector::getKey, Function.identity()));
	}

	private final String key;
	private final Function<Container, String> containerValueGetter;

	ContainerFileFormSelector(String key, Function<Container, String> containerValueGetter) {
		this.key = key;
		this.containerValueGetter = containerValueGetter;
	}

	public static String getValue(FileFormSchema.ColumDefinition columDefinition, Container container) {
		return Optional.ofNullable(columDefinition.getKey())
				.map(VALUE_MAP::get)
				.map(it -> it.containerValueGetter.apply(container))
				.orElse(null);
	}
}
