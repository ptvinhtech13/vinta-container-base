package io.vinta.containerbase.app.test;

import io.vinta.containerbase.common.enums.ContainerState;
import io.vinta.containerbase.common.enums.TransportEquipmentType;
import io.vinta.containerbase.common.idgenerator.ImportJobIdGenerator;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.mapstruct.MapstructCommonMapper;
import io.vinta.containerbase.core.containers.ContainerCommandService;
import io.vinta.containerbase.core.containers.request.CreateContainerCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
public class ContainerDummyGenerator {
	private final ContainerCommandService containerCommandService;

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		containerCommandService.createContainers(generateDummyContainers(5500));
	}

	private List<CreateContainerCommand> generateDummyContainers(int count) {
		List<CreateContainerCommand> containers = new ArrayList<>();
		Random random = new Random();

		// Common data arrays for random selection
		String[] shippingLines = { "MAERSK|MAEU|Maersk Line", "MSC|MSCU|Mediterranean Shipping Company",
				"CMA CGM|CMDU|CMA CGM Group", "COSCO|COSU|COSCO Shipping Lines", "EVERGREEN|EGLV|Evergreen Marine",
				"HAPAG|HLCU|Hapag-Lloyd", "ONE|ONEY|Ocean Network Express",
				"YANGMING|YMLU|Yang Ming Marine Transport" };

		String[] isoEquipmentCodes = { "22G1", "22R1", "42G1", "42R1", "45G1", "45R1", "20G1", "40G1" };
		String[] states = { ContainerState.AVAILABLE.name(), ContainerState.IN_USE.name(), ContainerState.MAINTENANCE
				.name(), ContainerState.DAMAGED.name(), ContainerState.FULL.name(), ContainerState.EMPTY.name() };

		String[] contents = { "General Cargo", "Electronics", "Textiles", "Automotive Parts", "Machine Parts",
				"Consumer Goods", "Raw Materials", "Chemicals", "Food Products", "Empty" };

		for (int i = 0; i < count; i++) {
			// Select random shipping line
			String[] lineInfo = shippingLines[random.nextInt(shippingLines.length)].split("\\|");
			String lineCode = lineInfo[0];
			String lineSCAC = lineInfo[1];
			String lineName = lineInfo[2];

			// Generate container number (4 letters + 7 numbers)
			String containerNumber = lineSCAC + String.format("%07d", random.nextInt(10000000));

			// Select random ISO code and determine if it's a reefer
			String isoCode = isoEquipmentCodes[random.nextInt(isoEquipmentCodes.length)];
			boolean isReefer = isoCode.contains("R1");

			// Generate random weights (in realistic ranges)
			int tareWeight = isReefer ? 4000 + random.nextInt(1500) : 2000 + random.nextInt(1000);
			int maxGrossWeight = 30000 + random.nextInt(5000);
			int payloadWeight = maxGrossWeight - tareWeight;

			containers.add(CreateContainerCommand.builder()
							.importJobId(MapstructCommonDomainMapper.INSTANCE.stringToImportJobId(String.format("%08d", (10000 + random.nextInt(30)))))
					.containerNumber(containerNumber)
					.isoEquipmentCode(isoCode)
					.equipmentReference("REF" + String.format("%06d", i))
					.transportEquipmentType(isReefer ? TransportEquipmentType.REEFER : TransportEquipmentType.DRY)
					.tareWeightKg(tareWeight)
					.maxGrossWeightKg(maxGrossWeight)
					.payloadWeightKg(payloadWeight)
					.isReefer(isReefer)
					.state(ContainerState.valueOf(states[random.nextInt(states.length)]))
					.damageDescription(random.nextInt(20) == 0 ? "Minor damage on panel" : null) // 5% chance of damage
					.bookingReference("BK" + String.format("%08d", (100000000 + random.nextInt(200))))
					.shipmentReference("SH" + String.format("%08d", (100000000 + random.nextInt(200))))
					.contentsDescription(contents[random.nextInt(contents.length)])
					.sealNumber("SL" + String.format("%06d", (100000000 + random.nextInt(200))))
					.sealSource(random.nextBoolean() ? "CARRIER" : "SHIPPER")
					.ownerShippingLineCode(lineCode)
					.ownerShippingSCAC(lineSCAC)
					.ownerName(lineName)
					.ownerAddress(String.format("%d Shipping Street, Singapore %06d", random.nextInt(100) + 1, random
							.nextInt(900000) + 100000))
					.build());
		}

		return containers;
	}
}
