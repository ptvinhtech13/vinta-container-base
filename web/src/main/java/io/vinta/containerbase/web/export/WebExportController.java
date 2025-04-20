package io.vinta.containerbase.web.export;

import io.vinta.containerbase.core.export.ExportJobQueryService;
import io.vinta.containerbase.core.export.request.FilterExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;
import io.vinta.containerbase.web.BaseWebController;
import io.vinta.containerbase.web.export.mapper.ExportJobViewMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class WebExportController implements BaseWebController {
	private final ExportJobQueryService queryService;

	@GetMapping("/containers/export")
	public String uploads(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {

		final var request = FindExportJobQuery.builder()
				.filter(FilterExportJob.builder()
						.byCreatedFrom(Optional.ofNullable(dateFrom)
								.map(it -> it.atZone(ZoneId.systemDefault())
										.toInstant())
								.orElse(null))
						.byCreatedTo(Optional.ofNullable(dateTo)
								.map(it -> it.atZone(ZoneId.systemDefault())
										.toInstant())
								.orElse(null))
						.build())
				.page(page - 1)
				.sortFields(List.of("id"))
				.sortDirection("DESC")
				.size(100)
				.build();

		final var result = queryService.queryExportJobs(request);

		model.addAttribute("jobs", result.getContent()
				.stream()
				.map(ExportJobViewMapper.INSTANCE::toView)
				.toList());
		model.addAttribute("currentPage", result.getPage());
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("pageSize", request.getSize());
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		return "containers/export";
	}

	@GetMapping("/containers/export/create")
	public String getExportPage() {
		return "containers/create-export";
	}
}
