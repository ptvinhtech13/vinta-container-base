package io.vinta.containerbase.web.warehouse;

import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.ZoneId;

import io.vinta.containerbase.web.warehouse.mapper.ContainerViewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebWarehouseController {

	private final ContainerQueryService queryService;

	@GetMapping("/containers/warehouse")
	public String warehouse(
			@RequestParam(required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
			@RequestParam(required = false) String importJobId,
			@RequestParam(required = false) String bookingReference,
			@RequestParam(required = false) String ownerShippingLineCode,
			@RequestParam(required = false) Set<String> containerNumbers,
			@RequestParam(defaultValue = "1") int page, Model model) {

		final var result = queryService.queryContainers(FindContainerQuery.builder()
				.filter(FilterContainer.builder()
						.byCreatedFrom(Optional.ofNullable(dateFrom)
								.map(it -> it.atZone(ZoneId.systemDefault())
								.toInstant())
								.orElse(null))
						.byCreatedTo(Optional.ofNullable(dateTo)
								.map(it -> it.atZone(ZoneId.systemDefault())
										.toInstant())
								.orElse(null))
						.byImportJobId(importJobId)
						.byBookingReference(bookingReference)
						.byOwnerShippingLineCode(ownerShippingLineCode)
						.byContainerNumbers(containerNumbers)
						.build())
				.page(page - 1)
				.sortFields(List.of("createdAt"))
				.sortDirection("DESC")
				.size(20)
				.build());

		model.addAttribute("containers", result.getContent().stream().map(ContainerViewMapper.INSTANCE::toView).toList());
		model.addAttribute("currentPage", result.getPage());
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("pageSize", result.getContent().size());
		model.addAttribute("totalElements", result.getTotalElements());

		return "containers/warehouse";
	}
}