package io.vinta.containerbase.rest.importjob;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.baseid.SheetFormId;
import io.vinta.containerbase.common.idgenerator.ImportJobIdGenerator;
import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.entities.FileDataSource;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import io.vinta.containerbase.rest.api.ImportJobApi;
import io.vinta.containerbase.rest.importjob.mapper.ImportJobResponseMapper;
import io.vinta.containerbase.rest.importjob.response.ImportJobResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImportJobController implements ImportJobApi {

	private final ImportJobCommandService service;
	private final Path uploadDir = Paths.get("./app-data/uploads");

	@SneakyThrows
	@Override
	public ImportJobResponse createImportJob(List<MultipartFile> files, List<String> sheetFormIds) {
		final var importJobId = ImportJobIdGenerator.randomId();
		final var fileDataSources = uploadFiles(importJobId, files, sheetFormIds.stream()
				.map(String::toUpperCase)
				.map(SheetFormId::valueOf)
				.collect(Collectors.toList()));
		return ImportJobResponseMapper.INSTANCE.toResponse(service.createImportJob(CreateImportJobCommand.builder()
				.id(importJobId)
				.sources(fileDataSources)
				.build()));
	}

	private Set<FileDataSource> uploadFiles(ImportJobId importJobId, List<MultipartFile> files,
			List<SheetFormId> sheetFormIds) {
		// Create container directory
		final var jobDir = uploadDir.resolve(importJobId.getValue());
		jobDir.toFile()
				.mkdirs();

		return IntStream.range(0, files.size())
				.mapToObj(index -> {
					final var file = files.get(index);
					final var sheetFormId = sheetFormIds.get(index);

					Path typeDir = jobDir.resolve(sheetFormId.name());
					try {
						Files.createDirectories(typeDir);
						// Save file
						Path filePath = typeDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
						Files.copy(file.getInputStream(), filePath);
						return FileDataSource.builder()
								.sheetFormId(sheetFormId)
								.path(filePath.toString())
								.build();
					} catch (IOException e) {
						log.error("Failed to create directory for type: {}", sheetFormId.name());
						throw new RuntimeException(e);
					}

				})
				.collect(Collectors.toSet());

	}
}
