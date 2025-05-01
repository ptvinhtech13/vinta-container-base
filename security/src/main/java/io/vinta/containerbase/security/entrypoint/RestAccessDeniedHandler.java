package io.vinta.containerbase.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.common.exceptions.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	public RestAccessDeniedHandler(ObjectMapper objectMapper) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException {
		log.info("Access denied: accessDeniedException: {}, request:{}", accessDeniedException, request
				.getRequestURI());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter()
				.write(objectMapper.writeValueAsString(ErrorResponse.builder()
						.errorCode(HttpServletResponse.SC_FORBIDDEN)
						.message(HttpStatus.FORBIDDEN.getReasonPhrase())
						.path(request.getRequestURI())
						.timestamp(Instant.now())
						.build()));
	}

}
