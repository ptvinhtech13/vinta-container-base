package io.vinta.containerbase.web.export;

import io.vinta.containerbase.core.importjob.ImportJobQueryService;
import io.vinta.containerbase.core.importjob.request.FilterImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;
import io.vinta.containerbase.web.BaseWebController;
import io.vinta.containerbase.web.export.mapper.ImportJobViewMapper;
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
public class WebExportController implements BaseWebController {
	private final ImportJobQueryService queryService;

	@GetMapping("/containers/export")
	public String uploads(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {

		final var request = FindImportJobQuery.builder()
				.filter(FilterImportJob.builder()
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
				.size(100)
				.build();

		final var result = queryService.queryImportJobs(request);

		model.addAttribute("jobs", result.getContent()
				.stream()
				.map(ImportJobViewMapper.INSTANCE::toView)
				.toList());
		model.addAttribute("currentPage", result.getPage());
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		model.addAttribute("pageSize", request.getSize());
		return "containers/export";
	}

	@GetMapping("/containers/export/create")
	public String getExportPage() {
		return "containers/create-export";
	}
}
