package io.vinta.containerbase.core.sheetforms.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class SheetFormSchema {

	private List<ColumDefinition> columDefinitions;

	@Builder.Default
	private int headerRowIndex = 1;

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
	}

}
