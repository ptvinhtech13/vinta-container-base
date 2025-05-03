package io.vinta.containerbase.tests.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.springframework.http.HttpHeaders;

@Builder
public record GenerateHttpHeader(HttpHeaders header,
		ObjectMapper objectMapper,
		String accessToken,
		Long requestId,
		Long tenantId) {
}
