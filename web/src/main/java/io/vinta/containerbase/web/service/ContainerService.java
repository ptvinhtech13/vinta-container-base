package io.vinta.containerbase.web.service;

import io.vinta.containerbase.web.dto.ContainerResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContainerService {

	private final Path uploadDir = Paths.get("uploads");

	public ContainerService() {
		try {
			Files.createDirectories(uploadDir);
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload directory!", e);
		}
	}

	public ContainerResponse createContainer(List<MultipartFile> files, List<String> types) {
		try {
			// Generate a unique container ID
			String containerId = "CONTAINER_" + UUID.randomUUID()
					.toString()
					.substring(0, 8)
					.toUpperCase();

			// Create container directory
			Path containerDir = uploadDir.resolve(containerId);
			Files.createDirectory(containerDir);

			// Save files
			for (int i = 0; i < files.size(); i++) {
				MultipartFile file = files.get(i);
				String type = types.get(i);

				// Create type directory if it doesn't exist
				Path typeDir = containerDir.resolve(type);
				Files.createDirectories(typeDir);

				// Save file
				Path filePath = typeDir.resolve(file.getOriginalFilename());
				Files.copy(file.getInputStream(), filePath);
			}

			return new ContainerResponse(containerId, "success");
		} catch (IOException e) {
			throw new RuntimeException("Failed to store files", e);
		}
	}
}