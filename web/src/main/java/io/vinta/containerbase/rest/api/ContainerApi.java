package io.vinta.containerbase.rest.api;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.rest.container.response.ContainerResponse;
import java.time.ZonedDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ContainerApi {
	@GetMapping("/api/containers")
	@ResponseBody
	Paging<ContainerResponse> queryContainer(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateTo,
			@RequestParam(defaultValue = "1") int page);
}
