package io.vinta.containerbase.core.inout.vintademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.enums.ImportJobStatus;
import io.vinta.containerbase.common.enums.ImportRecordStatus;
import io.vinta.containerbase.common.enums.ImportRecordType;
import io.vinta.containerbase.common.exceptions.ContainerBaseException;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.fallback.FallbackFlow;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.entities.ImportJobTrackingMetrics;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.core.inout.BaseFileFormInOut;
import io.vinta.containerbase.core.inout.FileFormImporter;
import io.vinta.containerbase.core.inout.models.ProcessedCsvResult;
import io.vinta.containerbase.core.inout.vintademo.mapper.VintaContainerMapper;
import io.vinta.containerbase.core.inout.vintademo.models.VintaDemoContainerImportModelData;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VintaDemoImporter extends BaseFileFormInOut implements FileFormImporter {

	private static final int BATCH_RECORD_MAX_SIZE = 500;
	private final ContainerVintaDemoCsvRowDataValidator rowDataValidator;
	private final ObjectMapper objectMapper;

	@Override
	public boolean hasSupport(FileFormId formId) {
		return new FileFormId("VINTA_DEMO").equals(formId);

	}

	@Override
	public ImportJob load(ImportJob job, Consumer<List<ImportRecord>> batchingRecordConsumer) {
		log.info("Loading job: {}", job);
		final var fileForm = fileFormManagerService.getFileForm(job.getFileFormId())
				.orElseThrow(() -> new NotFoundException("Do not support file form id: " + job.getFileFormId()
						.getValue()));

		final var result = processCsv(fileForm, job, batchingRecordConsumer);

		final var hasError = result.getTotalErrorRecords() > 0;
		log.info("Completed for loading job: {}", job);
		return importCommandService.updateImportJob(job.withStatus(hasError
				? ImportJobStatus.ERROR
				: ImportJobStatus.VALIDATED)
				.withConsolidatedErrorMessages(result.getConsolidatedErrorMessages())
				.withMetrics(ImportJobTrackingMetrics.builder()
						.totalRecords(result.getTotalRecords())
						.totalRecordsError(result.getTotalErrorRecords())
						.build()));
	}

	@Override
	@Transactional
	public List<ImportRecord> processRecords(List<ImportRecord> content) {
		final var containerLists = content.stream()
				.map(record -> VintaContainerMapper.INSTANCE.toCreateContainer(record.getImportJobId(), objectMapper
						.convertValue(record.getData(), VintaDemoContainerImportModelData.class)))
				.toList();
		containerCommandService.createContainers(containerLists);
		return content.stream()
				.map(it -> it.withRecordStatus(ImportRecordStatus.COMPLETED))
				.toList();
	}

	protected FileFormSchema buildCsvDataSchemaByHeader(String[] actualHeaders, FileFormSchema defaultSchema) {
		if (actualHeaders == null || actualHeaders.length == 0) {
			throw new ContainerBaseException("CSV Header values is required instead of empty.");
		}
		final var headerValues = List.of(actualHeaders);
		final var columnDefinitions = defaultSchema.getColumDefinitions()
				.stream()
				.map(it -> FallbackFlow.<FileFormSchema.ColumDefinition, FileFormSchema.ColumDefinition>builder()
						.addFallBack(col -> getIndexOfByIgnoreCase(col.getKey(), headerValues) != -1, col -> col
								.withIndex(getIndexOfByIgnoreCase(col.getKey(), headerValues)))
						.addFallBack(col -> getIndexOfByIgnoreCase(col.getColumnName(), headerValues) != -1, col -> col
								.withIndex(getIndexOfByIgnoreCase(col.getColumnName(), headerValues)))
						.addFallBack(col -> getIndexOfByIgnoreCaseContaining(col.getColumnName(), headerValues) != -1, col -> col
								.withIndex(getIndexOfByIgnoreCaseContaining(col.getColumnName(), headerValues)))
						.execute(it))
				.filter(Objects::nonNull)
				.toList();

		if (columnDefinitions.size() != defaultSchema.getColumDefinitions()
				.size()) {
			throw new ContainerBaseException("CSV Header was not well format. Expected: %s. Actual: %s".formatted(
					defaultSchema.getColumDefinitions()
							.stream()
							.map(it -> String.join(",", it.getKey(), it.getColumnName()))
							.collect(Collectors.joining(",")), Arrays.toString(actualHeaders)));
		}
		return defaultSchema.withColumDefinitions(columnDefinitions);
	}

	private static int getIndexOfByIgnoreCase(String checkingValue, List<String> values) {
		return values.stream()
				.map(String::toLowerCase)
				.toList()
				.indexOf(checkingValue.toLowerCase());
	}

	private static int getIndexOfByIgnoreCaseContaining(String checkingValue, List<String> values) {
		final var lowerCaseCheckingValue = checkingValue.toLowerCase();
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).toLowerCase().contains(lowerCaseCheckingValue)) {
				return i;
			}
		}
		return -1;
	}

	protected ProcessedCsvResult processCsv(FileForm fileForm, ImportJob job,
			Consumer<List<ImportRecord>> batchingRecordConsumer) {
		try (InputStream is = new FileInputStream(job.getUploadedFilePath());
				CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
			String[] rowData = null;
			FileFormSchema actualCsvSchema = null;
			long totalRecords = 0;
			long totalErrorRecords = 0;
			final var defaultSchema = fileForm.getSchema();
			final var records = new ArrayList<ImportRecord>();
			final var consolidatedErrorMessages = new StringBuilder();

			while ((rowData = csvReader.readNext()) != null) {
				if (defaultSchema.getHeaderRowIndex() == csvReader.getRecordsRead()) {
					actualCsvSchema = buildCsvDataSchemaByHeader(rowData, defaultSchema);
					job = importCommandService.updateImportJob(job.withActualSchema(actualCsvSchema));
					continue;
				}
				if (actualCsvSchema == null) {
					continue;
				}
				final var rowValidationResult = rowDataValidator.validateRow(actualCsvSchema, rowData);

				records.add(ImportRecord.builder()
						.importJobId(job.getId())
						.recordIndex(csvReader.getRecordsRead() - 1)
						.recordType(ImportRecordType.CONTAINER)
						.recordStatus(rowValidationResult.isValid()
								? ImportRecordStatus.VALIDATED
								: ImportRecordStatus.ERROR)
						.errorMessage(rowValidationResult.getErrorMessage())
						.data(rowValidationResult.getModelData())
						.rawData(Arrays.toString(rowData))
						.build());
				if (rowValidationResult.getErrorMessage() != null) {
					consolidatedErrorMessages.append(rowValidationResult.getErrorMessage())
							.append("\n");
				}
				if (records.size() >= BATCH_RECORD_MAX_SIZE) {
					batchingRecordConsumer.accept(new ArrayList<>(records));
					totalRecords = totalRecords + records.size();
					totalErrorRecords = totalErrorRecords + records.stream()
							.filter(it -> ImportRecordStatus.ERROR.equals(it.getRecordStatus()))
							.toList()
							.size();
					records.clear();
				}
			}

			if (!records.isEmpty()) {
				batchingRecordConsumer.accept(new ArrayList<>(records));
				totalRecords = totalRecords + records.size();
				totalErrorRecords = totalErrorRecords + records.stream()
						.filter(it -> ImportRecordStatus.ERROR.equals(it.getRecordStatus()))
						.toList()
						.size();
			}

			return ProcessedCsvResult.builder()
					.job(job)
					.totalRecords(totalRecords)
					.totalErrorRecords(totalErrorRecords)
					.consolidatedErrorMessages(consolidatedErrorMessages.toString())
					.build();
		} catch (Exception exception) {
			throw new ContainerBaseException(exception.getMessage(), exception);
		}
	}

}
