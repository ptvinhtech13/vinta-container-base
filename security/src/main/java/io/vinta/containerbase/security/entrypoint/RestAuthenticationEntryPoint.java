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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	public RestAuthenticationEntryPoint(ObjectMapper objectMapper) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException {

		log.info("Authentication exception: {}; request = {}", authException, request.getRequestURI());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter()
				.write(objectMapper.writeValueAsString(ErrorResponse.builder()
						.errorCode(HttpServletResponse.SC_UNAUTHORIZED)
						.message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
						.path(request.getRequestURI())
						.timestamp(Instant.now())
						.build()));

	}
}
