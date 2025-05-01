package io.vinta.containerbase.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.security.entrypoint.RestAccessDeniedHandler;
import io.vinta.containerbase.security.entrypoint.RestAuthenticationEntryPoint;
import io.vinta.containerbase.security.jwt.CORSFilter;
import io.vinta.containerbase.security.jwt.JwtAuthenticationFilter;
import io.vinta.containerbase.security.jwt.JwtTokenClaimExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class JwtSecurityConfig {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;

	@Autowired
	private JwtTokenClaimExtractor tokenClaimExtractor;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http, final ErrorCodeConfig errorCodeConfig)
			throws Exception {

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))
				.authorizeHttpRequests(authorize -> authorize.anyRequest()
						.authenticated())
				.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new RestAuthenticationEntryPoint(
						objectMapper))
						.accessDeniedHandler(new RestAccessDeniedHandler(objectMapper)));

		// Authentication. Verify token type and path.
		http.addFilterBefore(new JwtAuthenticationFilter(exceptionResolver, tokenClaimExtractor, errorCodeConfig),
				UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(new CORSFilter(), JwtAuthenticationFilter.class);

		//        http.addFilterAfter(
		//                new HttpAuthorizationPermissionFilter(errorCodeConfig,
		//                        authorizationRuleConfig,
		//                        httpAuthorizationPermissionVerifier),
		//                JwtAuthenticationFilter.class);

		log.info("Vinta Spring Security has been enabled");

		return http.build();
	}

}
