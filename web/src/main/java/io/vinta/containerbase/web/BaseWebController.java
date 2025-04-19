package io.vinta.containerbase.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface BaseWebController {
	@ModelAttribute("currentUri")
	default String currentUri(HttpServletRequest request) {
		return request.getRequestURI();
	}
}
