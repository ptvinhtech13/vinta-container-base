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
package io.vinta.containerbase.app.test;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestExport {

	private final ExportJobCommandService exportJobCommandService;

	@EventListener(ApplicationReadyEvent.class)
	public void test() {
		exportJobCommandService.createExportJob(CreateExportJobCommand.builder()
				.exportFormId(new FileFormId("VINTA_DEMO"))
				.filterContainer(FilterContainer.builder()
						//						.byOwnerShippingLineCode("ONE")
						//						.byBookingReference("BK63019871")
						.build())
				.build());
	}
}
