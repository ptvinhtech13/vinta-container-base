package io.vinta.containerbase.tests.http.controller.roles;

import io.vinta.containerbase.common.constants.TenantConstants;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.ApiPermissionKey;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.common.security.permissions.FeatureNodeType;
import io.vinta.containerbase.core.role.request.CreateRoleCommand;
import io.vinta.containerbase.rest.role.request.CreateRoleRequest;
import io.vinta.containerbase.rest.role.request.QueryRolePaginationRequest;
import io.vinta.containerbase.rest.role.request.QueryRoleRequest;
import io.vinta.containerbase.rest.role.request.UpdateRoleRequest;
import io.vinta.containerbase.rest.role.response.FeatureNodeResponse;
import io.vinta.containerbase.rest.role.response.RoleResponse;
import io.vinta.containerbase.tests.commons.BaseIntegrationTest;
import io.vinta.containerbase.tests.commons.utils.GenerateHttpHeader;
import io.vinta.containerbase.tests.commons.utils.HeaderGenerator;
import io.vinta.containerbase.tests.config.IntegrationTestConfiguration;
import java.util.Arrays;
import java.util.Collection;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
class RoleControllerIntegrationTest extends BaseIntegrationTest {

	@Test
	void testCreateRoleWhenValidRequestThenReturn() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var request = CreateRoleRequest.builder()
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId()
						.getValue(), ApiPermissionKey.TENANT_MGMT_UPDATE.getId()
								.getValue(), ApiPermissionKey.TENANT_MGMT_VIEW.getId()
										.getValue(), ApiPermissionKey.USER_MGMT_VIEW.getId()
												.getValue()))
				.build();

		// Act & Assert
		webClient.post()
				.uri("/api/role/roles")
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
				.expectBody(RoleResponse.class)
				.consumeWith(response -> {
					final var roleResponse = response.getResponseBody();
					Assertions.assertNotNull(roleResponse);
					Assertions.assertEquals(request.getTitle(), roleResponse.getTitle());
					Assertions.assertEquals(request.getDescription(), roleResponse.getDescription());
					Assertions.assertEquals(4, roleResponse.getFeatureNodes()
							.size());
					Assertions.assertTrue(roleResponse.getFeatureNodes()
							.stream()
							.allMatch(it -> it.getNodeType() == FeatureNodeType.API));
					roleResponse.getFeatureNodes()
							.forEach(actualFeatureNode -> {
								final var expected = request.getFeatureNodeIds()
										.stream()
										.filter(it -> it.equals(Long.valueOf(actualFeatureNode.getId())))
										.map(it -> Stream.of(ApiPermissionKey.values())
												.filter(key -> key.getId()
														.getValue()
														.equals(it))
												.findFirst()
												.orElseThrow(() -> new RuntimeException("Feature node not found")))
										.findFirst()
										.orElseThrow(() -> new RuntimeException("Feature node not found"));
								Assertions.assertEquals(expected.getId()
										.getValue(), Long.valueOf(actualFeatureNode.getId()));
								Assertions.assertEquals(expected.getNodeTitle(), actualFeatureNode.getNodeTitle());
								Assertions.assertEquals(expected.getNodeType(), actualFeatureNode.getNodeType());
								Assertions.assertEquals(expected.getDisplayOrder(), actualFeatureNode
										.getDisplayOrder());
							});
				});
	}

	@Test
	void testGetRoleByKeyWhenValidRequestThenReturn() {
		// Act & Assert
		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/role/roles/keys/{roleKey}")
						.build(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey()))
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(TenantConstants.ADMIN_TENANT_ID)
						.build()))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(RoleResponse.class)
				.consumeWith(response -> {
					final var roleResponse = response.getResponseBody();
					Assertions.assertNotNull(roleResponse);
					Assertions.assertEquals(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleTitle(), roleResponse
							.getTitle());
					Assertions.assertEquals(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey(), roleResponse
							.getRoleKey());

					final var featureNodes = Stream.of(ApiPermissionKey.values())
							.filter(it -> FeatureNodeType.MODULE.equals(it.getNodeType()))
							.map(it -> featureNodeQueryService.getChildrenNodeByParentPath(it.getNodePath()))
							.flatMap(Collection::stream)
							.toList();

					Assertions.assertEquals(featureNodes.size(), roleResponse.getFeatureNodes()
							.size());
				});
	}

	@Test
	void testGetFeatureNodeTreeWhenValidRequestThenReturnSuccessfully() {
		// Act & Assert
		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/feature-nodes/tree")
						.build(DefaultSystemRole.SYSTEM_ADMIN_ROLE.getRoleKey()))
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.objectMapper(objectMapper)
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.requestId(1L)
						.tenantId(TenantConstants.ADMIN_TENANT_ID)
						.build()))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(FeatureNodeResponse.class)
				.consumeWith(responses -> {
					final var moduleNodes = responses.getResponseBody();
					Assertions.assertNotNull(moduleNodes);

					Assertions.assertEquals(Arrays.stream(ApiPermissionKey.values())
							.filter(it -> FeatureNodeType.MODULE.equals(it.getNodeType()))
							.count(), moduleNodes.size());

					moduleNodes.forEach(moduleNode -> {
						final var apiNodes = featureNodeQueryService.getChildrenNodeByParentPath(moduleNode
								.getNodePath());
						Assertions.assertEquals(apiNodes.size(), moduleNode.getChildren()
								.size());

						Assertions.assertTrue(apiNodes.stream()
								.allMatch(apiNode -> moduleNode.getChildren()
										.stream()
										.anyMatch(it -> it.getKey()
												.equals(apiNode.name()))));
					});
				});
	}

	@Test
	void testGetRoleByIdWhenValidRequestThenReturn() {

		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var role = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		// Act & Assert
		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/role/roles/{roleId}")
						.build(role.getId()
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
				.expectBody(RoleResponse.class)
				.consumeWith(response -> {
					final var roleResponse = response.getResponseBody();
					Assertions.assertNotNull(roleResponse);
					Assertions.assertEquals(role.getTitle(), roleResponse.getTitle());
					Assertions.assertEquals(role.getDescription(), roleResponse.getDescription());
					Assertions.assertEquals(role.getRoleKey(), roleResponse.getRoleKey());

					Assertions.assertEquals(role.getFeatureNodeIds()
							.size(), roleResponse.getFeatureNodes()
									.size());
				});
	}

	@Test
	void testUpdateRoleByIdWhenValidRequestThenReturnSuccessfully() {

		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var role = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var request = UpdateRoleRequest.builder()
				.title("New Role 2")
				.description("New Role 2")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_VIEW.getId()
						.getValue(), ApiPermissionKey.USER_MGMT_VIEW.getId()
								.getValue()))
				.build();

		// Act & Assert
		webClient.put()
				.uri(uriBuilder -> uriBuilder.path("/api/role/roles/{roleId}")
						.build(role.getId()
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
				.expectBody(RoleResponse.class)
				.consumeWith(response -> {
					final var roleResponse = response.getResponseBody();
					Assertions.assertNotNull(roleResponse);
					Assertions.assertEquals(request.getTitle(), roleResponse.getTitle());
					Assertions.assertEquals(request.getDescription(), roleResponse.getDescription());

					Assertions.assertEquals(request.getFeatureNodeIds()
							.size(), roleResponse.getFeatureNodes()
									.size());
				});

	}

	@Test
	void testDeleteRoleByIdWhenValidRequestThenReturnSuccessfully() {
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var role = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		// Act & Assert
		webClient.delete()
				.uri(uriBuilder -> uriBuilder.path("/api/role/roles/{roleId}")
						.build(role.getId()
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
				.expectBody(Void.class)
				.consumeWith(response -> {
					Assertions.assertTrue(true);

					Assertions.assertThrows(NotFoundException.class, () -> roleQueryService.getRole(tenant.getId(), role
							.getId()));
				});
	}

	@Test
	void testQueryAllRolesWhenValidRequestThenReturnSuccessfully() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant987654.com");
		final var role1 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var role2 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 2")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var role3 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 2")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var request = QueryRolePaginationRequest.builder()
				.page(0)
				.size(10)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build();

		final Map<String, String> requestParamMap = new HashMap<>();
		requestParamMap.put("filter.byTenantId", tenant.getId()
				.getValue()
				.toString());
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

					uriBuilder.path("/api/role/roles");
					uriBuilder.queryParams(requestParams);
					return uriBuilder.build();
				})
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.build()))
				.exchange()
				.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
				.expectStatus()
				.isOk()
				.expectBody(Paging.class)
				.consumeWith(response -> {
					final var roleResponse = Optional.ofNullable(response.getResponseBody())
							.map(Paging::getContent)
							.map(ls -> (List<RoleResponse>) ls.stream()
									.map(it -> objectMapper.convertValue(it, RoleResponse.class))
									.toList())
							.orElseGet(List::of);
					Assertions.assertEquals(Arrays.stream(DefaultSystemRole.values())
							.filter(it -> !it.isDefaultSystemTenant())
							.count() + 3, roleResponse.size());
				});
	}

	@Test
	void testQueryAllRolesByRoleIdsWhenValidRequestThenReturnSuccessfully() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant987655.com");
		final var role1 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 1")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var role2 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 2")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var role3 = roleSupporter.createRole(CreateRoleCommand.builder()
				.tenantId(tenant.getId())
				.title("New Role 2")
				.description("New Role 1")
				.featureNodeIds(Set.of(ApiPermissionKey.TENANT_MGMT_CREATE.getId(), ApiPermissionKey.TENANT_MGMT_UPDATE
						.getId(), ApiPermissionKey.TENANT_MGMT_VIEW.getId(), ApiPermissionKey.USER_MGMT_VIEW.getId()))
				.build());

		final var request = QueryRolePaginationRequest.builder()
				.filter(QueryRoleRequest.builder()
						.byRoleIds(Set.of(role1.getId()
								.getValue()
								.toString(), role2.getId()
										.getValue()
										.toString(), role3.getId()
												.getValue()
												.toString()))
						.build())
				.page(0)
				.size(10)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build();

		final Map<String, String> requestParamMap = new HashMap<>();
		requestParamMap.put("filter.byRoleIds", String.join(",", request.getFilter()
				.getByRoleIds()));
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

					uriBuilder.path("/api/role/roles");
					uriBuilder.queryParams(requestParams);
					return uriBuilder.build();
				})
				.headers(header -> HeaderGenerator.generateHttpHeaders(GenerateHttpHeader.builder()
						.header(header)
						.requestId(1L)
						.tenantId(tenant.getId()
								.getValue())
						.accessToken(accessTokenSupporter.loginAsSuperAdmin())
						.build()))
				.exchange()
				.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
				.expectStatus()
				.isOk()
				.expectBody(Paging.class)
				.consumeWith(response -> {
					final var roleResponse = Optional.ofNullable(response.getResponseBody())
							.map(Paging::getContent)
							.map(ls -> (List<RoleResponse>) ls.stream()
									.map(it -> objectMapper.convertValue(it, RoleResponse.class))
									.toList())
							.orElseGet(List::of);
					Assertions.assertEquals(3, roleResponse.size());
				});
	}
}
