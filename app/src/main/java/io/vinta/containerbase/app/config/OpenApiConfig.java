package io.vinta.containerbase.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.vinta.containerbase.common.constants.SecurityConstants;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme().name(
						securitySchemeName)
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}

	@Bean
	public OperationCustomizer tenantHeaderCustomizer() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			Parameter tenantIdHeader = new Parameter().in("header")
					.name(SecurityConstants.X_TENANT_ID)
					.description("Tenant ID")
					.required(false)
					.schema(new StringSchema());

			operation.addParametersItem(tenantIdHeader);
			return operation;
		};
	}
}