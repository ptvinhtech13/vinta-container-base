package io.vinta.containerbase.core.sheetforms.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.SheetFormId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@AllArgsConstructor
@Builder
@With
public class SheetForm extends BaseEntity<SheetFormId> {
	private SheetFormId id;

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