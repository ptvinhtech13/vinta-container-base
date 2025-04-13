/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.core.containers.request;

import io.vinta.containerbase.common.enums.ContainerState;
import io.vinta.containerbase.common.enums.TransportEquipmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateContainerCommand {
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
	private final String ownerShippingLineSCAC;
	private final String ownerName;
	private final String ownerAddress;
}