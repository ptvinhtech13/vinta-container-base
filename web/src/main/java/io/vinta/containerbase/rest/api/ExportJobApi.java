package io.vinta.containerbase.rest.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ExportJobApi {
	@GetMapping("/api/export/{exportId}/download")
	ResponseEntity<Resource> download(@PathVariable("exportId") Long exportId);
}
