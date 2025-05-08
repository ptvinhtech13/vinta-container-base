package io.vinta.containerbase.tests.http.controller.dashboard;

import io.vinta.containerbase.common.baseid.DashboardType;
import io.vinta.containerbase.common.baseid.TenantId;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.constants.TenantConstants;
import io.vinta.containerbase.common.enums.DashboardStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.dashboard.DashboardCommandService;
import io.vinta.containerbase.core.dashboard.DashboardQueryService;
import io.vinta.containerbase.core.dashboard.entities.DashboardAccessPolicy;
import io.vinta.containerbase.core.dashboard.request.CreateDashboardCommand;
import io.vinta.containerbase.rest.dashboard.request.CreateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.DashboardAccessPolicyRequest;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardPaginationRequest;
import io.vinta.containerbase.rest.dashboard.request.QueryDashboardRequest;
import io.vinta.containerbase.rest.dashboard.request.UpdateDashboardRequest;
import io.vinta.containerbase.rest.dashboard.response.DashboardResponse;
import io.vinta.containerbase.tests.commons.BaseIntegrationTest;
import io.vinta.containerbase.tests.commons.utils.GenerateHttpHeader;
import io.vinta.containerbase.tests.commons.utils.HeaderGenerator;
import io.vinta.containerbase.tests.config.IntegrationTestConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
class DashboardControllerIntegrationTest extends BaseIntegrationTest {

	@Autowired
	private DashboardCommandService dashboardCommandService;

	@Autowired
	private DashboardQueryService dashboardQueryService;

	@Test
	void testCreateDashboardWhenValidRequest() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var request = CreateDashboardRequest.builder()
				.name("New Dashboard 1")
				.description("New Dashboard 1")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(1L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicyRequest.builder()
						.allowedTenantIds(Set.of(tenant.getId()
								.getValue()
								.toString()))
						.build())
				.build();

		// Act & Assert
		webClient.post()
				.uri("/api/dashboard/dashboards")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.build()))
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(DashboardResponse.class)
				.consumeWith(response -> {
					final var dashboardResponse = response.getResponseBody();
					Assertions.assertNotNull(dashboardResponse);
					Assertions.assertEquals(request.getName(), dashboardResponse.getName());
					Assertions.assertEquals(request.getDescription(), dashboardResponse.getDescription());
					Assertions.assertEquals(request.getDashboardType(), dashboardResponse.getDashboardType());
					Assertions.assertEquals(request.getStatus(), dashboardResponse.getStatus());
					Assertions.assertEquals(request.getMetabaseId(), dashboardResponse.getMetabaseId());
					Assertions.assertTrue(dashboardResponse.getAccessPolicy()
							.getAllowedTenantIds()
							.containsAll(Stream.of(TenantConstants.ADMIN_TENANT_ID.toString(), tenant.getId()
									.getValue()
									.toString())
									.toList()));
				});
	}

	@Test
	void testUpdateDashboardWhenValidRequest() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");

		final var dashboard = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 1")
				.description("New Dashboard 1")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedTenantIds(Set.of(tenant.getId()))
						.build())
				.build());

		final var request = UpdateDashboardRequest.builder()
				.name("New Dashboard 2")
				.description("New Dashboard 2")
				.metabaseId(4321L)
				.status(DashboardStatus.DISABLED)
				.accessPolicy(DashboardAccessPolicyRequest.builder()
						.allowedTenantIds(Set.of(tenant.getId()
								.getValue()
								.toString()))
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of("123", "456"))
						.build())
				.build();

		// Act & Assert
		webClient.put()
				.uri(uriBuilder -> uriBuilder.path("/api/dashboard/dashboards/{dashboardId}")
						.build(dashboard.getId()
								.getValue()))
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.build()))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(DashboardResponse.class)
				.consumeWith(response -> {
					final var dashboardResponse = response.getResponseBody();
					Assertions.assertNotNull(dashboardResponse);
					Assertions.assertEquals(dashboard.getId()
							.getValue()
							.toString(), dashboardResponse.getId());
					Assertions.assertEquals(dashboard.getDashboardType(), dashboardResponse.getDashboardType());

					Assertions.assertEquals(request.getName(), dashboardResponse.getName());
					Assertions.assertEquals(request.getDescription(), dashboardResponse.getDescription());
					Assertions.assertEquals(request.getStatus(), dashboardResponse.getStatus());
					Assertions.assertEquals(request.getMetabaseId(), dashboardResponse.getMetabaseId());
					Assertions.assertTrue(dashboardResponse.getAccessPolicy()
							.getAllowedTenantIds()
							.containsAll(Stream.of(TenantConstants.ADMIN_TENANT_ID.toString(), tenant.getId()
									.getValue()
									.toString())
									.toList()));
					Assertions.assertEquals(request.getAccessPolicy()
							.getAllowedUserTypes(), dashboardResponse.getAccessPolicy()
									.getAllowedUserTypes());
				});
	}

	@Test
	void testGetDashboardWhenValidRequest() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var dashboard = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 1")
				.description("New Dashboard 1")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedTenantIds(Set.of(tenant.getId()))
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of(new UserId(123L), new UserId(456L)))
						.build())
				.build());

		// Act & Assert
		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/dashboard/dashboards/{dashboardId}")
						.build(dashboard.getId()
								.getValue()))
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.build()))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(DashboardResponse.class)
				.consumeWith(response -> {
					final var dashboardResponse = response.getResponseBody();
					Assertions.assertNotNull(dashboardResponse);
					Assertions.assertEquals(dashboard.getId()
							.getValue()
							.toString(), dashboardResponse.getId());
					Assertions.assertEquals(dashboard.getDashboardType(), dashboardResponse.getDashboardType());

					Assertions.assertEquals(dashboard.getName(), dashboardResponse.getName());
					Assertions.assertEquals(dashboard.getDescription(), dashboardResponse.getDescription());
					Assertions.assertEquals(dashboard.getStatus(), dashboardResponse.getStatus());
					Assertions.assertEquals(dashboard.getMetabaseId(), dashboardResponse.getMetabaseId());
					Assertions.assertEquals(dashboard.getAccessPolicy()
							.getAllowedTenantIds()
							.stream()
							.map(TenantId::getValue)
							.map(Object::toString)
							.collect(Collectors.toSet()), dashboardResponse.getAccessPolicy()
									.getAllowedTenantIds());
					Assertions.assertEquals(dashboard.getAccessPolicy()
							.getAllowedUserTypes(), dashboardResponse.getAccessPolicy()
									.getAllowedUserTypes());
					Assertions.assertEquals(dashboard.getAccessPolicy()
							.getAllowedUserIds()
							.stream()
							.map(UserId::getValue)
							.map(Object::toString)
							.collect(Collectors.toSet()), dashboardResponse.getAccessPolicy()
									.getAllowedUserIds());
				});
	}

	@Test
	void testQueryDashboardsByAdminInAdminTenantWhenValidRequest() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenantQuery1.com");
		final var dashboard1 = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 1")
				.description("New Dashboard 1")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedTenantIds(Set.of(tenant.getId()))
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of(new UserId(123L), new UserId(456L)))
						.build())
				.build());
		final var dashboard2 = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 2")
				.description("New Dashboard 2")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedTenantIds(Set.of(tenant.getId()))
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of(new UserId(123L), new UserId(456L)))
						.build())
				.build());
		final var dashboard3 = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 3")
				.description("New Dashboard 3")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of(new UserId(123L), new UserId(456L)))
						.build())
				.build());

		final var request = QueryDashboardPaginationRequest.builder()
				.filter(QueryDashboardRequest.builder()
						.byTenantIds(Set.of(tenant.getId()
								.getValue()
								.toString()))
						.build())
				.page(0)
				.size(10)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build();

		final Map<String, String> requestParamMap = new HashMap<>();
		requestParamMap.put("filter.byTenantIds", request.getFilter()
				.getByTenantIds()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining(",")));

		requestParamMap.put("size", String.valueOf(request.getSize()));
		requestParamMap.put("page", String.valueOf(request.getPage()));
		requestParamMap.put("sortDirection", "ASC");
		requestParamMap.put("sortFields", String.join(",", request.getSortFields()));

		// Act & Assert
		webClient.get()
				.uri(uriBuilder -> {
					final LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>(requestParamMap
							.entrySet()
							.stream()
							.filter(it -> StringUtils.isNotBlank(it.getValue()))
							.collect(Collectors.toMap(Map.Entry::getKey, it -> List.of(it.getValue()))));

					uriBuilder.path("/api/dashboard/dashboards");
					uriBuilder.queryParams(requestParams);
					return uriBuilder.build();
				})
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.requestId(1L)
						.tenantId(TenantConstants.ADMIN_TENANT_ID)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.build()))
				.exchange()
				.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
				.expectStatus()
				.isOk()
				.expectBody(Paging.class)
				.consumeWith(response -> {
					final var dashboardResponse = Optional.ofNullable(response.getResponseBody())
							.map(Paging::getContent)
							.map(ls -> (List<DashboardResponse>) ls.stream()
									.map(it -> objectMapper.convertValue(it, DashboardResponse.class))
									.toList())
							.orElseGet(List::of);
					Assertions.assertEquals(2, dashboardResponse.size());
				});
	}

	@Test
	void testDeleteDashboardWhenValidRequest() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var dashboard = dashboardCommandService.createDashboard(CreateDashboardCommand.builder()
				.name("New Dashboard 1")
				.description("New Dashboard 1")
				.dashboardType(DashboardType.DASHBOARD)
				.metabaseId(134L)
				.status(DashboardStatus.ENABLED)
				.accessPolicy(DashboardAccessPolicy.builder()
						.allowedTenantIds(Set.of(tenant.getId()))
						.allowedUserTypes(Set.of(UserType.BACK_OFFICE))
						.allowedUserIds(Set.of(new UserId(123L), new UserId(456L)))
						.build())
				.build());

		// Act & Assert
		webClient.delete()
				.uri(uriBuilder -> uriBuilder.path("/api/dashboard/dashboards/{dashboardId}")
						.build(dashboard.getId()
								.getValue()))
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.build()))
				.exchange()
				.expectStatus()
				.isNoContent()
				.expectBody(Void.class)
				.consumeWith(response -> {
					Assertions.assertTrue(dashboardQueryService.getDashboard(dashboard.getId())
							.isEmpty());
				});
	}
}
