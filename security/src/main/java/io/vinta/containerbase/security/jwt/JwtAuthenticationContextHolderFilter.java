package io.vinta.containerbase.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.vinta.containerbase.common.constants.SecurityConstants;
import io.vinta.containerbase.common.exceptions.AuthenticationException;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.exceptions.ForbiddenException;
import io.vinta.containerbase.common.exceptions.InternalServerErrorException;
import io.vinta.containerbase.common.exceptions.TokenExpiredException;
import io.vinta.containerbase.common.exceptions.UnauthorizedException;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.common.security.context.HttpSecurityContext;
import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationContextHolderFilter extends OncePerRequestFilter {

	private final ErrorCodeConfig errorCodeConfig;

	private final HandlerExceptionResolver exceptionResolver;

	private final JwtTokenClaimExtractor tokenClaimExtractor;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) {
		try {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();

			if (authentication != null && authentication.isAuthenticated() && authentication
					.getPrincipal() instanceof Jwt jwt) {
				// Extract the token claim from the JWT
				final var tokenClaim = (JwtTokenClaim) jwt.getClaims()
						.get("tokenClaim");

				// Set up the security context
				AppSecurityContextHolder.setContext(HttpSecurityContext.httpSecurityContextBuilder()
						.authorizationToken(tokenClaimExtractor.extractToken(request))
						.tokenClaim(tokenClaim)
						.tenantId(MapstructCommonDomainMapper.INSTANCE.stringToTenantId(getHeaderIgnoreCase(request,
								SecurityConstants.X_TENANT_ID)))
						.userId(MapstructCommonDomainMapper.INSTANCE.longToUserId(tokenClaim.getUserId()))
						.userType(tokenClaim.getUserType())
						.build());
			} else {
				AppSecurityContextHolder.setContext(HttpSecurityContext.httpSecurityContextBuilder()
						.tenantId(MapstructCommonDomainMapper.INSTANCE.stringToTenantId(getHeaderIgnoreCase(request,
								SecurityConstants.X_TENANT_ID)))
						.build());
			}
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
			AppSecurityContextHolder.clearContext();
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
}
