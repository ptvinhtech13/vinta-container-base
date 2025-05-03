package io.vinta.containerbase.security.jwt;

import io.vinta.containerbase.common.constants.SecurityConstants;
import io.vinta.containerbase.common.exceptions.AuthenticationException;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenClaimExtractor {

	private final JwtTokenAccessManager tokenAccessManager;
	private final ErrorCodeConfig errorCodeConfig;

	//    @Value("${io.vinta.websocket.endpoints:}")
	//    private List<String> wsEndpoints;

	public FullTokenClaim fromAuthorizationToken(final HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("Extracting token in: {}", request.getRequestURI());
		}

		final var tokenAsString = extractToken(request);
		final var fullTokenClaim = tokenAccessManager.verifyAndDecodeToken(tokenAsString);

		if (log.isDebugEnabled()) {
			log.debug("Token in: {} {}", tokenAsString, fullTokenClaim);
		}
		return fullTokenClaim;
	}

	public String extractToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(SecurityConstants.AUTHORIZATION))
				.orElseThrow(() -> new AuthenticationException(errorCodeConfig.getMessage(
						CommonErrorConstants.AUTHORIZATION_MUST)));
	}
}
