/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.rest.export;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.common.exceptions.ContainerBaseException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.core.export.ExportJobQueryService;
import io.vinta.containerbase.rest.api.ExportJobApi;
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

	@SneakyThrows
	@Override
	public ResponseEntity<Resource> download(Long exportId) {

		final var exportJob = exportJobQueryService.getExportJob(MapstructCommonDomainMapper.INSTANCE.longToExportJobId(
				exportId))
				.orElseThrow();

		if (!ExportJobStatus.SUCCEEDED.equals(exportJob.getStatus())) {
			throw new ContainerBaseException("Only succeeded export job can be downloaded");
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
}
