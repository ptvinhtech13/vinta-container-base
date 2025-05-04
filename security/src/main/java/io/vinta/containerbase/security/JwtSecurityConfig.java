package io.vinta.containerbase.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import io.vinta.containerbase.security.apiregistry.ApiInfoRegistry;
import io.vinta.containerbase.security.entrypoint.RestAccessDeniedHandler;
import io.vinta.containerbase.security.entrypoint.RestAuthenticationEntryPoint;
import io.vinta.containerbase.security.jwt.CORSFilter;
import io.vinta.containerbase.security.jwt.JwtAuthenticationContextHolderFilter;
import io.vinta.containerbase.security.jwt.JwtTokenAccessManager;
import io.vinta.containerbase.security.jwt.JwtTokenClaimExtractor;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class JwtSecurityConfig {

	private final ObjectMapper objectMapper;
	private final JwtTokenAccessManager jwtTokenAccessManager;
	private final ApiInfoRegistry apiInfoRegistry;

	@Autowired
	public JwtSecurityConfig(ObjectMapper objectMapper, JwtTokenAccessManager jwtTokenAccessManager,
			ApiInfoRegistry apiInfoRegistry) {
		this.objectMapper = objectMapper;
		this.jwtTokenAccessManager = jwtTokenAccessManager;
		this.apiInfoRegistry = apiInfoRegistry;
	}

	@Bean
	public SecurityFilterChain apiSecurityChain(final HttpSecurity http,
			@Value("${io.vinta.containerbase.security.api.authorization.enabled}") boolean isEnabledAuthorization,
			ApiInfoRegistry apiInfoRegistry, ErrorCodeConfig errorCodeConfig,
			JwtTokenClaimExtractor tokenClaimExtractor,
			@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))

				.authorizeHttpRequests(authorize -> List.of("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**",
						"/swagger-ui.html", "/webjars/**")
						.forEach(path -> authorize.requestMatchers(path)
								.permitAll()))

				.authorizeHttpRequests(authorize -> apiInfoRegistry.getPublicApis()
						.forEach(apiInfo -> authorize.requestMatchers(apiInfo.getMethod(), apiInfo.getPath())
								.permitAll()))
				.authorizeHttpRequests(authorize -> apiInfoRegistry.getAuthenticatedApis()
						.forEach(apiInfo -> authorize.requestMatchers(apiInfo.getMethod(), apiInfo.getPath())
								.authenticated()))
				.authorizeHttpRequests(authorize -> {
					if (isEnabledAuthorization) {
						authorize.anyRequest()
								.denyAll();
					} else {
						authorize.anyRequest()
								.authenticated();
					}
				})
				.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new RestAuthenticationEntryPoint(
						objectMapper))
						.accessDeniedHandler(new RestAccessDeniedHandler(objectMapper)))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(
						jwtAuthenticationConverter()))
						.authenticationEntryPoint(new RestAuthenticationEntryPoint(objectMapper)));

		http.addFilterAfter(new JwtAuthenticationContextHolderFilter(errorCodeConfig, exceptionResolver,
				tokenClaimExtractor), BearerTokenAuthenticationFilter.class);
		http.addFilterBefore(new CORSFilter(), UsernamePasswordAuthenticationFilter.class);

		log.info("Vinta Spring Security has been enabled");

		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		// Create a custom JwtDecoder that uses your JwtTokenAccessManager
		return token -> {
			try {
				FullTokenClaim fullTokenClaim = jwtTokenAccessManager.verifyAndDecodeToken(token);
				Map<String, Object> claims = new HashMap<>();
				claims.put("tokenClaim", fullTokenClaim.getTokenClaim());
				return new Jwt(token, Instant.ofEpochSecond(fullTokenClaim.getIat()), Instant.ofEpochSecond(
						fullTokenClaim.getExp()), Map.of("alg", "ECDSA512", "typ", "JWT"), claims);
			} catch (Exception e) {
				log.warn("Failed to decode JWT token", e);
				throw new JwtException("Invalid JWT token", e);
			}
		};
	}

	@Bean
	public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			final var tokenClaim = (JwtTokenClaim) jwt.getClaims()
					.get("tokenClaim");
			if (tokenClaim != null && tokenClaim.getUserType() != null) {
				return Collections.singletonList(new SimpleGrantedAuthority(tokenClaim.getUserType()
						.name()));
			}
			return Collections.emptyList();
		});

		converter.setPrincipalClaimName("tokenClaim");
		return converter;
	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}
}
