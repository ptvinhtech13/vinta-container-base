package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.rest.importjob.response.ImportJobResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ImportJobApi {
	@PostMapping("/api/import-job/create")
	ImportJobResponse createImportJob(@RequestParam("file") MultipartFile file,
			@RequestParam("fileFormId") String fileFormId, @RequestParam("remark") String remark);
}
