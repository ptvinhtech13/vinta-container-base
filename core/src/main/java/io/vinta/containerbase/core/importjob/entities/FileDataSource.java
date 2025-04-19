package io.vinta.containerbase.core.importjob.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vinta.containerbase.common.baseid.SheetFormId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class FileDataSource {
	private SheetFormId sheetFormId;
	private String path;
}
