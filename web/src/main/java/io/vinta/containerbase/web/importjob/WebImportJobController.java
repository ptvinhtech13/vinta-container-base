/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.web.importjob;

import io.vinta.containerbase.core.importjob.ImportJobQueryService;
import io.vinta.containerbase.core.importjob.request.FilterImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;
import io.vinta.containerbase.web.BaseWebController;
import io.vinta.containerbase.web.importjob.mapper.ImportJobViewMapper;
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
public class WebImportJobController implements BaseWebController {
	private final ImportJobQueryService queryService;

	@GetMapping("/containers/import-jobs")
	public String uploads(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo,
			@RequestParam(defaultValue = "1") int page, Model model) {

		final var result = queryService.queryImportJobs(FindImportJobQuery.builder()
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
				.size(3)
				.build());

		model.addAttribute("jobs", result.getContent()
				.stream()
				.map(ImportJobViewMapper.INSTANCE::toView)
				.toList());
		model.addAttribute("currentPage", result.getPage());
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		return "containers/import";
	}

	@GetMapping("/containers/import-jobs/create")
	public String getCreateImportJobPage() {
		return "containers/uploads";
	}
}
