package io.vinta.containerbase.rest.container.response;

import io.vinta.containerbase.common.enums.ContainerState;
import io.vinta.containerbase.common.enums.TransportEquipmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ContainerResponse {
	private final String id;
	private final String importJobId;
	private final String containerNumber;
	private final String isoEquipmentCode;
	private final String equipmentReference;
	private final TransportEquipmentType transportEquipmentType;
	private final Integer tareWeightKg;
	private final Integer maxGrossWeightKg;
	private final Integer payloadWeightKg;
	private final boolean isReefer;
	private final ContainerState state;
	private final String damageDescription;
	private final String bookingReference;
	private final String shipmentReference;
	private final String contentsDescription;
	private final String sealNumber;
	private final String sealSource;
	private final String ownerShippingLineCode;
	private final String ownerShippingSCAC;
	private final String ownerName;
	private final String ownerAddress;
}
