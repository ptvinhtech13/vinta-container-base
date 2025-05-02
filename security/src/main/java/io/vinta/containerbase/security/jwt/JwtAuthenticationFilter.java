package io.vinta.containerbase.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.vinta.containerbase.common.exceptions.AuthenticationException;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.exceptions.ForbiddenException;
import io.vinta.containerbase.common.exceptions.InternalServerErrorException;
import io.vinta.containerbase.common.exceptions.TokenExpiredException;
import io.vinta.containerbase.common.exceptions.UnauthorizedException;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.security.constants.SecurityConstants;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.common.security.context.HttpSecurityContext;
import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import io.vinta.containerbase.security.domains.JwtUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final HandlerExceptionResolver exceptionResolver;
	private final JwtTokenClaimExtractor tokenClaimExtractor;
	private final ErrorCodeConfig errorCodeConfig;

	public JwtAuthenticationFilter(final HandlerExceptionResolver exceptionResolver,
			final JwtTokenClaimExtractor tokenClaimExtractor, final ErrorCodeConfig errorCodeConfig) {
		this.exceptionResolver = exceptionResolver;
		this.tokenClaimExtractor = tokenClaimExtractor;
		this.errorCodeConfig = errorCodeConfig;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) {

		final FullTokenClaim fullTokenClaim;
		try {
			log.info("Attempting Authentication: request = {}", request.getRequestURI());
			fullTokenClaim = parseTokenAndAuthenticate(request);
			final var userDetails = new JwtUserDetails(fullTokenClaim.getTokenClaim());
			final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails
					.getAuthorities());

			AppSecurityContextHolder.setContext(HttpSecurityContext.httpSecurityContextBuilder()
					.authorizationToken(tokenClaimExtractor.extractToken(request))
					.fullTokenClaim(fullTokenClaim)
					.tenantId(MapstructCommonDomainMapper.INSTANCE.stringToTenantId(getHeaderIgnoreCase(request,
							SecurityConstants.X_TENANT_ID)))
					.userId(MapstructCommonDomainMapper.INSTANCE.longToUserId(fullTokenClaim.getTokenClaim()
							.getUserId()))
					.userType(fullTokenClaim.getTokenClaim()
							.getUserType())
					.build());
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (final TokenExpiredException ex) {
			log.warn("Token has been expired.");
			exceptionResolver.resolveException(request, response, null, ex);
		} catch (final AuthenticationException ex) {
			log.warn("Token has been must.");
			exceptionResolver.resolveException(request, response, null, ex);
		} catch (final JWTDecodeException ex) {
			log.warn("Token Decoding has error.");
			exceptionResolver.resolveException(request, response, null, new UnauthorizedException(errorCodeConfig
					.getMessage(CommonErrorConstants.TOKEN_INVALID)));
		} catch (final JWTVerificationException ex) {
			log.warn("Token Verification has error.");
			exceptionResolver.resolveException(request, response, null, new UnauthorizedException(errorCodeConfig
					.getMessage(CommonErrorConstants.TOKEN_INVALID)));
		} catch (final ForbiddenException ex) {
			log.warn("Forbidden API Accessing.");
			exceptionResolver.resolveException(request, response, null, ex);
		} catch (final BadRequestException ex) {
			log.warn("Bad Request Error.");
			exceptionResolver.resolveException(request, response, null, ex);
		} catch (final Exception ex) {
			log.warn("Unexpected Error.");
			exceptionResolver.resolveException(request, response, null, new InternalServerErrorException(errorCodeConfig
					.getMessage(CommonErrorConstants.INTERNAL_SERVER_ERROR), ex.getCause()));
		} finally {
			clearLocalThreadData();
		}
	}

	private String getHeaderIgnoreCase(HttpServletRequest request, String headerName) {
		return Collections.list(request.getHeaderNames())
				.stream()
				.filter(name -> name.equalsIgnoreCase(headerName))
				.findFirst()
				.map(request::getHeader)
				.orElse(null);
	}

	private void clearLocalThreadData() {
		SecurityContextHolder.clearContext();
		AppSecurityContextHolder.clearContext();
	}

	private FullTokenClaim parseTokenAndAuthenticate(final HttpServletRequest request) {
		try {
			return tokenClaimExtractor.fromAuthorizationToken(request);
		} catch (final com.auth0.jwt.exceptions.TokenExpiredException expiredException) {
			throw new TokenExpiredException(errorCodeConfig.getMessage(CommonErrorConstants.TOKEN_EXPIRED));
		}
	}
}
