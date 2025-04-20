package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.rest.export.request.CreateExportJobRequest;
import io.vinta.containerbase.rest.export.response.ExportJobResponse;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ExportJobApi {
	@GetMapping("/api/export/{exportId}/download")
	ResponseEntity<Resource> download(@PathVariable("exportId") Long exportId);

	@PostMapping("/api/export")
	ExportJobResponse createExportJob(@Valid @RequestBody CreateExportJobRequest request);

}
