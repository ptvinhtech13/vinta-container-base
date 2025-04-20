package io.vinta.containerbase.app.fileform;

import io.vinta.containerbase.app.fileform.config.FileFormConfigProperties;
import io.vinta.containerbase.app.fileform.mapper.FileFormConfigPropertiesMapper;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileFormConfigInitializer {
	private final FileFormConfigProperties fileFormConfigProperties;
	private final FileFormManagerService fileFormManagerService;

	@PostConstruct
	private void init() {
		fileFormManagerService.init(fileFormConfigProperties.getSupportedFileForms()
				.stream()
				.map(FileFormConfigPropertiesMapper.INSTANCE::toFileForm)
				.toList());
	}
}
