package io.vinta.containerbase.app.test;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class TestImport {

	private final ImportJobCommandService importJobCommandService;

	@EventListener(ApplicationReadyEvent.class)
	public void test() {
		importJobCommandService.createImportJob(CreateImportJobCommand.builder()
				.id(new ImportJobId("TEST#" + System.currentTimeMillis()))
				.fileFormId(new FileFormId("VINTA_DEMO"))
				.uploadedFilePath("./app-data/exports/1363499884572794880-container-list.csv")
				.build());
	}
}
