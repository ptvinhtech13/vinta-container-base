package io.vinta.containerbase.web.warehouse;

import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebWarehouseController {

	private final ContainerQueryService queryService;

	@GetMapping("/containers/warehouse")
	public String warehouse(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {

		final var result = queryService.queryContainers(FindContainerQuery.builder()
				.filter(FilterContainer.builder()
						.byCreatedFrom(Optional.ofNullable(dateFrom)
								.map(ZonedDateTime::toInstant)
								.orElse(null))
						.byCreatedTo(Optional.ofNullable(dateTo)
								.map(ZonedDateTime::toInstant)
								.orElse(null))
						.build())
				.page(page - 1)
				.sortFields(List.of("createdAt"))
				.sortDirection("DESC")
				.size(20)
				.build());

		model.addAttribute("containers", result.getContent());
		model.addAttribute("currentPage", result.getPage());
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		return "containers/warehouse";
	}
}