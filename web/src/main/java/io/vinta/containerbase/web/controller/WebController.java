package io.vinta.containerbase.web.controller;

import io.vinta.containerbase.web.model.Container;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	private static final int PAGE_SIZE = 10;
	private final List<Container> dummyContainers;

	public WebController() {
		// Initialize dummy data
		dummyContainers = new ArrayList<>();
		dummyContainers.add(new Container("nginx-web", "abc123def456", LocalDateTime.now()
				.minusDays(1)));
		dummyContainers.add(new Container("mysql-db", "xyz789uvw321", LocalDateTime.now()
				.minusDays(2)));
		dummyContainers.add(new Container("redis-cache", "mno456pqr789", LocalDateTime.now()
				.minusDays(3)));
		dummyContainers.add(new Container("mongodb", "def987ghi654", LocalDateTime.now()
				.minusDays(4)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
		dummyContainers.add(new Container("postgres-db", "jkl321mno654", LocalDateTime.now()
				.minusDays(5)));
	}

	@ModelAttribute("currentUri")
	public String currentUri(HttpServletRequest request) {
		return request.getRequestURI();
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/containers/input")
	public String uploads(@RequestParam(required = false) String containerId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {
		List<Container> filteredContainers = dummyContainers.stream()
				.filter(container -> {
					if (containerId != null && !containerId.isEmpty()) {
						return container.getContainerId()
								.contains(containerId);
					}
					return true;
				})
				.filter(container -> {
					if (dateFrom != null) {
						return !container.getCreatedAt()
								.isBefore(dateFrom);
					}
					return true;
				})
				.filter(container -> {
					if (dateTo != null) {
						return !container.getCreatedAt()
								.isAfter(dateTo);
					}
					return true;
				})
				.collect(Collectors.toList());

		// Calculate pagination
		int totalItems = filteredContainers.size();
		int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
		int start = (page - 1) * PAGE_SIZE;
		int end = Math.min(start + PAGE_SIZE, totalItems);

		List<Container> pageContainers = filteredContainers.subList(start, end);

		model.addAttribute("containers", pageContainers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("containerId", containerId);
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);

		return "containers/input";
	}

	@GetMapping("/containers/export")
	public String export(@RequestParam(required = false) String containerId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {
		List<Container> filteredContainers = dummyContainers.stream()
				.filter(container -> {
					if (containerId != null && !containerId.isEmpty()) {
						return container.getContainerId()
								.contains(containerId);
					}
					return true;
				})
				.filter(container -> {
					if (dateFrom != null) {
						return !container.getCreatedAt()
								.isBefore(dateFrom);
					}
					return true;
				})
				.filter(container -> {
					if (dateTo != null) {
						return !container.getCreatedAt()
								.isAfter(dateTo);
					}
					return true;
				})
				.collect(Collectors.toList());

		// Calculate pagination
		int totalItems = filteredContainers.size();
		int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
		int start = (page - 1) * PAGE_SIZE;
		int end = Math.min(start + PAGE_SIZE, totalItems);

		List<Container> pageContainers = filteredContainers.subList(start, end);

		model.addAttribute("containers", pageContainers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("containerId", containerId);
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);

		return "containers/export";
	}

	@GetMapping("/settings")
	public String settings() {
		return "settings";
	}

	@GetMapping("/containers/input/create")
	public String createContainer() {
		return "containers/uploads";
	}

	@GetMapping("/containers/export/create")
	public String exportContainer() {
		return "containers/create-export";
	}
}