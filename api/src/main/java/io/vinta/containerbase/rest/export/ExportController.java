package io.vinta.containerbase.rest.export;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.core.export.ExportJobCommandService;
import io.vinta.containerbase.core.export.ExportJobQueryService;
import io.vinta.containerbase.rest.api.ExportJobApi;
import io.vinta.containerbase.rest.export.mapper.ExportJobRequestMapper;
import io.vinta.containerbase.rest.export.mapper.ExportJobResponseMapper;
import io.vinta.containerbase.rest.export.request.CreateExportJobRequest;
import io.vinta.containerbase.rest.export.response.ExportJobResponse;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExportController implements ExportJobApi {
	private final ExportJobQueryService exportJobQueryService;
	private final ExportJobCommandService exportCommandService;

	@SneakyThrows
	@Override
	public ResponseEntity<Resource> download(Long exportId) {

		final var exportJob = exportJobQueryService.getExportJob(MapstructCommonDomainMapper.INSTANCE.longToExportJobId(
				exportId))
				.orElseThrow();

		if (!ExportJobStatus.SUCCEEDED.equals(exportJob.getStatus())) {
			throw new BadRequestException("Only succeeded export job can be downloaded");
		}

		final var filePath = Paths.get(exportJob.getFileOutputPath());
		Resource resource = new UrlResource(filePath.toUri());

		if (!resource.exists()) {
			return ResponseEntity.notFound()
					.build();
		}

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()
						+ "\"")
				.body(resource);

	}

	@Override
	public ExportJobResponse createExportJob(CreateExportJobRequest request) {
		return ExportJobResponseMapper.INSTANCE.toResponse(exportCommandService.createExportJob(
				ExportJobRequestMapper.INSTANCE.toCreateExportJobCommand(request)));

	}
}
