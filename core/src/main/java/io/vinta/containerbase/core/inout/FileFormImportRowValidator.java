package io.vinta.containerbase.core.inout;

import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import io.vinta.containerbase.core.inout.models.RowValidationResult;
import jakarta.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public interface FileFormImportRowValidator {
	default RowValidationResult validateRow(FileFormSchema csvSchema, String[] rowData) {
		try {
			return validate(csvSchema, rowData);
		} catch (Exception exception) {
			LoggerFactory.getLogger(this.getClass())
					.error(exception.getMessage(), exception);
			return RowValidationResult.builder()
					.isValid(false)
					.errorMessage(exception.getMessage())
					.build();
		}
	}

	default <T> String getErrorMessage(Set<ConstraintViolation<T>> results) {
		return results.stream()
				.map(it -> "%s: %s".formatted(it.getPropertyPath(), Optional.ofNullable(it.getMessageTemplate())
						.orElseGet(it::getMessage)))
				.collect(Collectors.joining("; "));
	}

	RowValidationResult validate(FileFormSchema csvSchema, String[] rowData);

	default Optional<String> getColumnDataByKey(FileFormSchema csvSchema, String[] rowData, String key) {
		return csvSchema.getColumDefinitions()
				.stream()
				.filter(it -> key.equals(it.getKey()))
				.findFirst()
				.map(FileFormSchema.ColumDefinition::getIndex)
				.filter(index -> rowData.length > index)
				.map(index -> rowData[index])
				.filter(StringUtils::isNotBlank);
	}
}
