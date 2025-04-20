package io.vinta.containerbase.core.inout.mapper;

import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum ContainerFileFormMapper {
	CONTAINER_NUMBER("containerNumber", Container::getContainerNumber),
	ISO_EQUIPMENT_CODE("isoEquipmentCode", Container::getIsoEquipmentCode),
	EQUIPMENT_REFERENCE("equipmentReference", Container::getEquipmentReference),
	TRANSPORT_EQUIPMENT_TYPE("transportEquipmentType", container -> container.getTransportEquipmentType()
			.name()),
	TARE_WEIGHT_KG("tareWeightKg", container -> container.getTareWeightKg()
			.toString()),
	MAX_GROSS_WEIGHT_KG("maxGrossWeightKg", container -> container.getMaxGrossWeightKg()
			.toString()),;

	private final static Map<String, ContainerFileFormMapper> VALUE_MAP = Map.of(CONTAINER_NUMBER.getKey(),
			CONTAINER_NUMBER, ISO_EQUIPMENT_CODE.getKey(), ISO_EQUIPMENT_CODE, EQUIPMENT_REFERENCE.getKey(),
			EQUIPMENT_REFERENCE, TRANSPORT_EQUIPMENT_TYPE.getKey(), TRANSPORT_EQUIPMENT_TYPE, TARE_WEIGHT_KG.getKey(),
			TARE_WEIGHT_KG, MAX_GROSS_WEIGHT_KG.getKey(), MAX_GROSS_WEIGHT_KG);

	private final String key;
	private final Function<Container, String> containerValueGetter;

	ContainerFileFormMapper(String key, Function<Container, String> containerValueGetter) {
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
