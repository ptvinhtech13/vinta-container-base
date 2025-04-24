package io.vinta.containerbase.core.fileform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@With
public class FileFormSchema {

	private List<ColumDefinition> columDefinitions;

	@Builder.Default
	private int headerRowIndex = 1;

	@JsonIgnore
	public List<String> getHeaderRow() {
		return columDefinitions.stream()
				.sorted(Comparator.comparingInt(ColumDefinition::getIndex))
				.map(ColumDefinition::getColumnName)
				.toList();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonIgnoreProperties(ignoreUnknown = true)
	@With
	public static class ColumDefinition {

		@Builder.Default
		private int index = -1;

		private String key;

		private String columnName;
	}

}
