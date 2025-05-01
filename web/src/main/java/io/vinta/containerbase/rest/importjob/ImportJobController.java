package io.vinta.containerbase.rest.importjob;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.idgenerator.ImportJobIdGenerator;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import io.vinta.containerbase.rest.api.ImportJobApi;
import io.vinta.containerbase.rest.importjob.mapper.ImportJobResponseMapper;
import io.vinta.containerbase.rest.importjob.response.ImportJobResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public 	class ImportJobController implements ImportJobApi {

	private final ImportJobCommandService service;
	private final FileFormManagerService fileFormManagerService;
	private final Path uploadDir = Paths.get("./app-data/uploads");

	@SneakyThrows
	@Override
	public ImportJobResponse createImportJob(MultipartFile file, String fileFormId, String remark) {
		final var importJobId = ImportJobIdGenerator.randomId();
		final var uploadedFilePath = uploadFiles(importJobId, file, fileFormId);
		return ImportJobResponseMapper.INSTANCE.toResponse(service.createImportJob(CreateImportJobCommand.builder()
				.id(importJobId)
				.fileFormId(new FileFormId(fileFormId))
				.uploadedFilePath(uploadedFilePath)
				.remark(remark)
				.build()));
	}

	private String uploadFiles(ImportJobId importJobId, MultipartFile file, String fileFormId) {
		final var jobDir = uploadDir.resolve(importJobId.getValue());
		jobDir.toFile()
				.mkdirs();

		Path typeDir = jobDir.resolve(fileFormId);
		try {
			Files.createDirectories(typeDir);
			// Save file
			Path filePath = typeDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
			Files.copy(file.getInputStream(), filePath);

			return filePath.toString();
		} catch (IOException e) {
			log.error("Failed to create directory for type: {}", fileFormId);
			throw new RuntimeException(e);
		}
	}
}
