package io.vinta.containerbase.core.inout.vintademo;

import com.opencsv.CSVWriter;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import io.vinta.containerbase.core.inout.BaseFileFormInOut;
import io.vinta.containerbase.core.inout.FileFormExporter;
import io.vinta.containerbase.core.inout.mapper.ContainerFileFormMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VintaDemoExporter extends BaseFileFormInOut implements FileFormExporter {

	private static final Integer MAX_BATCH_SIZE = 300;

	@Override
	public boolean hasSupport(FileFormId formId) {
		return new FileFormId("VINTA_DEMO").equals(formId);

	}

	@Override
	public ExportJob export(ExportJob job) {
		if (ExportJobStatus.CREATED.equals(job.getStatus())) {
			final var containers = containerQueryService.queryContainers(FindContainerQuery.builder()
					.filter(job.getFilterContainer())
					.page(0)
					.size(MAX_BATCH_SIZE)
					.build());
			job = exportCommandService.updateExportJob(job.withStatus(ExportJobStatus.EXPORTING)
					.withFileOutputPath("./app-data/exports/%s-container-list.csv".formatted(job.getId()
							.getValue()))
					.withTotalContainer(containers.getTotalElements()))
					.withTotalPage(containers.getTotalPages());

			exportToFilePath(Paths.get(job.getFileOutputPath()), job.getExportForm(), containers.getContent());
			final var isSucceeded = Objects.equals(job.getTotalContainer(), (long) containers.getContent()
					.size());

			return exportCommandService.updateExportJob(job.withStatus(isSucceeded
					? ExportJobStatus.SUCCEEDED
					: ExportJobStatus.EXPORTING))
					.withTotalExportedContainer((long) containers.getContent()
							.size())
					.withLastExportedPage(0);
		}

		final var containers = containerQueryService.queryContainers(FindContainerQuery.builder()
				.filter(job.getFilterContainer())
				.page(job.getLastExportedPage() + 1)
				.size(MAX_BATCH_SIZE)
				.build());

		exportToFilePath(Paths.get(job.getFileOutputPath()), job.getExportForm(), containers.getContent());

		final var isSucceeded = Objects.equals(job.getTotalContainer(), job.getTotalExportedContainer() + containers
				.getContent()
				.size());

		return exportCommandService.updateExportJob(job.withStatus(isSucceeded
				? ExportJobStatus.SUCCEEDED
				: ExportJobStatus.EXPORTING))
				.withTotalExportedContainer(job.getTotalExportedContainer() + containers.getContent()
						.size())
				.withLastExportedPage(job.getLastExportedPage() + 1);
	}

	@SneakyThrows
	private void exportToFilePath(Path path, FileForm form, List<Container> containers) {
		boolean fileExists = Files.exists(path);

		createFileIfNotExists(path);

		final var columns = form.getSchema()
				.getColumDefinitions();
		columns.sort(Comparator.comparingInt(FileFormSchema.ColumDefinition::getIndex));

		try (CSVWriter writer = new CSVWriter(new FileWriter(path.toFile(), true))) {
			if (!fileExists) {
				writer.writeNext(form.getSchema()
						.getHeaderRow()
						.toArray(String[]::new));
			}
			log.info("Writing {} rows to {}", containers.size(), path.getFileName());
			final var rows = containers.stream()
					.map(container -> columns.stream()
							.map(columDefinition -> ContainerFileFormMapper.getValue(columDefinition, container))
							.toArray(String[]::new))
					.toList();
			writer.writeAll(rows);
			log.info("Wrote {} rows to {}", rows.size(), path.getFileName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
