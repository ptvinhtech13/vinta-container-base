package io.vinta.containerbase.app.test;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.export.request.CreateExportJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

//@Component
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
