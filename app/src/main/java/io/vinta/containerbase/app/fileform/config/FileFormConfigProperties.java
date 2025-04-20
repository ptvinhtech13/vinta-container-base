package io.vinta.containerbase.app.fileform.config;

import io.vinta.containerbase.common.enums.FileFormAction;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "io.vinta.containerbase")
@RequiredArgsConstructor
public class FileFormConfigProperties {
	private List<FileFormConfig> supportedFileForms;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FileFormConfig {
		private String fileFormId;
		private String fileFormName;
		private Set<FileFormAction> actions;
		private FileFormSchema schema;
	}
}
