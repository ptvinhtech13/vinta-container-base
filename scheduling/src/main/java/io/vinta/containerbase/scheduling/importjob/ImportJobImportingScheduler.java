package io.vinta.containerbase.scheduling.importjob;

import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.core.importjob.ImportJobLoaderService;
import io.vinta.containerbase.core.importjob.ImportJobProcessorService;
import io.vinta.containerbase.core.importjob.ImportJobQueryService;
import io.vinta.containerbase.core.importjob.request.FilterImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;
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
@ConditionalOnProperty(name = "io.vinta.containerbase.import-job-importer.scheduling.enabled", havingValue = "true")
@RequiredArgsConstructor
@DependsOn("schedulingConfiguration")
public class ImportJobImportingScheduler {

	private final ImportJobQueryService queryService;

	private final ImportJobLoaderService loaderService;

	private final ImportJobProcessorService processorService;

	@PostConstruct
	public void init() {
		log.info("ImportJobImportingScheduler has been enabled");
	}

	@Scheduled(cron = "${io.vinta.containerbase.export-job.scheduling.cron-expression}")
	@SchedulerLock(name = "ImportJob.ImportJobLoadingScheduler", lockAtLeastFor = "${io.vinta.containerbase.import-job-importer.scheduling.lock-at-least-for:PT10S}", lockAtMostFor = "${io.vinta.containerbase.import-job-importer.scheduling.lock-at-most-for:PT10M}")
	public void triggerImportJobLoader() {
		LockAssert.assertLocked();

		final var pageResult = queryService.queryImportJobs(FindImportJobQuery.builder()
				.filter(FilterImportJob.builder()
						.byStatuses(Set.of(ImportJobStatus.CREATED, ImportJobStatus.LOADING))
						.build())
				.page(0)
				.size(1)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build());

		final var job = pageResult.getContent()
				.getFirst();
		loaderService.loadRecords(job);
	}

	@Scheduled(cron = "${io.vinta.containerbase.export-job.scheduling.cron-expression}")
	@SchedulerLock(name = "ImportJob.ImportJobProcessingScheduler", lockAtLeastFor = "${io.vinta.containerbase.import-job-processor.scheduling.lock-at-least-for:PT10S}", lockAtMostFor = "${io.vinta.containerbase.import-job-processor.scheduling.lock-at-most-for:PT10M}")
	public void triggerImportJobProcessing() {
		LockAssert.assertLocked();

		final var pageResult = queryService.queryImportJobs(FindImportJobQuery.builder()
				.filter(FilterImportJob.builder()
						.byStatuses(Set.of(ImportJobStatus.VALIDATED, ImportJobStatus.IMPORTING))
						.build())
				.page(0)
				.size(1)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build());

		final var job = pageResult.getContent()
				.getFirst();
		processorService.processImport(job);
	}

}
