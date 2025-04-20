package io.vinta.containerbase.scheduling.exportjob;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.export.ExportJobQueryService;
import io.vinta.containerbase.core.export.request.FilterExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "io.vinta.containerbase.export-job.scheduling.enabled", havingValue = "true")
@RequiredArgsConstructor
@DependsOn("schedulingConfiguration")
public class ExportJobScheduler {

	private final ExportJobQueryService exportJobQueryService;
	private final ExportJobCommandService exportJobCommandService;
	private final FileFormManagerService fileFormManagerService;

	@PostConstruct
	public void init() {
		log.info("ExportJobScheduler has been enabled");
	}

	@Scheduled(cron = "${io.vinta.containerbase.export-job.scheduling.cron-expression}")
	@SchedulerLock(name = "Export.ExportJobScheduler", lockAtLeastFor = "${io.vinta.containerbase.export-job.scheduling.lock-at-least-for:PT10S}", lockAtMostFor = "${io.vinta.containerbase.export-job.scheduling.lock-at-most-for:PT10M}")
	public void startExportScheduling() {
		LockAssert.assertLocked();
		final var result = exportJobQueryService.queryExportJobs(FindExportJobQuery.builder()
				.filter(FilterExportJob.builder()
						.byStatuses(Set.of(ExportJobStatus.CREATED, ExportJobStatus.EXPORTING))
						.build())
				.size(1)
				.page(0)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build());

		if (result.getTotalElements() == 0) {
			return;
		}
		var newJob = result.getContent()
				.get(0);
		log.info("Exporting job: {}", newJob.getId());

		try {
			final var formId = newJob.getExportForm()
					.getId();
			final var exporter = fileFormManagerService.getExporter(newJob.getExportForm()
					.getId())
					.orElseThrow(() -> new BadRequestException("No exporter found for form: " + formId));
			newJob = exporter.export(newJob);
			exportJobCommandService.updateExportJob(newJob);
		} catch (Exception ex) {
			log.error("Failed to export job: {}", newJob.getId(), ex);
			exportJobCommandService.updateExportJob(newJob.withStatus(ExportJobStatus.ERROR)
					.withRemark(ex.getMessage()));
		}

	}

}
