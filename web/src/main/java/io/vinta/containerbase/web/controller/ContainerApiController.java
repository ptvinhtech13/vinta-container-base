package io.vinta.containerbase.web.controller;

import io.vinta.containerbase.web.dto.ContainerResponse;
import io.vinta.containerbase.web.service.ContainerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/containers")
public class ContainerApiController {

	private final ContainerService containerService;

	@Autowired
	public ContainerApiController(ContainerService containerService) {
		this.containerService = containerService;
	}

	@PostMapping("/input/create")
	public ResponseEntity<ContainerResponse> createContainer(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("types") List<String> types) {

		ContainerResponse response = containerService.createContainer(files, types);
		return ResponseEntity.ok(response);
	}
}