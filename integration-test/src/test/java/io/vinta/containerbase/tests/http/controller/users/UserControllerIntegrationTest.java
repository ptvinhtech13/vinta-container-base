package io.vinta.containerbase.tests.http.controller.users;

import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.users.request.CreateUserCommand;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import io.vinta.containerbase.rest.user.request.CreateUserAccessBasicAuthRequest;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.CreateUserRoleRequest;
import io.vinta.containerbase.rest.user.request.DeleteUserRequest;
import io.vinta.containerbase.rest.user.request.QueryUserPaginationRequest;
import io.vinta.containerbase.rest.user.request.UpdateUserRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
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
class UserControllerIntegrationTest extends BaseIntegrationTest {

	@Test
	void testCreateTenantOwnerWhenValidRequestThenReturnUserResponse() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var userRole = roleQueryService.queryRoles(RolePaginationQuery.builder()
				.filter(FilterRoleQuery.builder()
						.byTenantId(tenant.getId())
						.build())
				.build())
				.getContent()
				.stream()
				.filter(it -> DefaultSystemRole.TENANT_OWNER_ROLE.getRoleKey()
						.equals(it.getRoleKey()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Role SALE_MANAGER_ROLE not found"));

		final var request = CreateUserRequest.builder()
				.email("tenantowner-001@vinta.com")
				.fullName("Full Name")
				.phoneNumber("1234567890")
				.userType(UserType.BACK_OFFICE)
				.userAccess(CreateUserAccessBasicAuthRequest.builder()
						.password("0000")
						.build())
				.userRole(CreateUserRoleRequest.builder()
						.roleId(userRole.getId()
								.getValue())
						.build())
				.build();

		// Act & Assert
		webClient.post()
				.uri("/api/user/users")
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
				.expectBody(UserResponse.class)
				.consumeWith(response -> {
					final var userResponse = response.getResponseBody();
					Assertions.assertNotNull(userResponse);
					Assertions.assertEquals(UserType.BACK_OFFICE, userResponse.getUserType());
					Assertions.assertEquals(request.getEmail(), userResponse.getEmail());
					Assertions.assertEquals(request.getFullName(), userResponse.getFullName());
					Assertions.assertEquals(request.getPhoneNumber(), userResponse.getPhoneNumber());
					Assertions.assertEquals(1, userResponse.getUserRoles()
							.size());
					final var userRoleResponse = userResponse.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					Assertions.assertEquals(userRole.getId()
							.getValue()
							.toString(), userRoleResponse.getRoleId());
					Assertions.assertEquals(userRole.getTenantId()
							.getValue()
							.toString(), userRoleResponse.getTenantId());
				});
	}

	@Test
	void testCreateSaleManagerWhenValidRequestThenReturnUserResponse() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var userRole = roleQueryService.queryRoles(RolePaginationQuery.builder()
				.filter(FilterRoleQuery.builder()
						.byTenantId(tenant.getId())
						.build())
				.build())
				.getContent()
				.stream()
				.filter(it -> DefaultSystemRole.SALE_MANAGER_ROLE.getRoleKey()
						.equals(it.getRoleKey()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Role SALE_MANAGER_ROLE not found"));

		final var request = CreateUserRequest.builder()
				.email("salemanager-001@vinta.com")
				.fullName("Full Name")
				.phoneNumber("1234567890")
				.userType(UserType.BACK_OFFICE)
				.userAccess(CreateUserAccessBasicAuthRequest.builder()
						.password("0000")
						.build())
				.userRole(CreateUserRoleRequest.builder()
						.roleId(userRole.getId()
								.getValue())
						.build())
				.build();

		// Act & Assert
		webClient.post()
				.uri("/api/user/users")
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
				.expectBody(UserResponse.class)
				.consumeWith(response -> {
					final var userResponse = response.getResponseBody();
					Assertions.assertNotNull(userResponse);
					Assertions.assertEquals(UserType.BACK_OFFICE, userResponse.getUserType());
					Assertions.assertEquals(request.getEmail(), userResponse.getEmail());
					Assertions.assertEquals(request.getFullName(), userResponse.getFullName());
					Assertions.assertEquals(request.getPhoneNumber(), userResponse.getPhoneNumber());
					Assertions.assertEquals(1, userResponse.getUserRoles()
							.size());
					final var userRoleResponse = userResponse.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					Assertions.assertEquals(userRole.getId()
							.getValue()
							.toString(), userRoleResponse.getRoleId());
					Assertions.assertEquals(userRole.getTenantId()
							.getValue()
							.toString(), userRoleResponse.getTenantId());
				});
	}

	@Test
	void testCreateSaleMemberWhenValidRequestThenReturnUserResponse() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var userRole = roleQueryService.queryRoles(RolePaginationQuery.builder()
				.filter(FilterRoleQuery.builder()
						.byTenantId(tenant.getId())
						.build())
				.build())
				.getContent()
				.stream()
				.filter(it -> DefaultSystemRole.SALE_MEMBER_ROLE.getRoleKey()
						.equals(it.getRoleKey()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Role SALE_MEMBER_ROLE not found"));

		final var request = CreateUserRequest.builder()
				.email("salemember-001@vinta.com")
				.fullName("Full Name")
				.phoneNumber("1234567890")
				.userType(UserType.BACK_OFFICE)
				.userAccess(CreateUserAccessBasicAuthRequest.builder()
						.password("0000")
						.build())
				.userRole(CreateUserRoleRequest.builder()
						.roleId(userRole.getId()
								.getValue())
						.build())
				.build();

		// Act & Assert
		webClient.post()
				.uri("/api/user/users")
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
				.expectBody(UserResponse.class)
				.consumeWith(response -> {
					final var userResponse = response.getResponseBody();
					Assertions.assertNotNull(userResponse);
					Assertions.assertEquals(UserType.BACK_OFFICE, userResponse.getUserType());
					Assertions.assertEquals(request.getEmail(), userResponse.getEmail());
					Assertions.assertEquals(request.getFullName(), userResponse.getFullName());
					Assertions.assertEquals(request.getPhoneNumber(), userResponse.getPhoneNumber());
					Assertions.assertEquals(1, userResponse.getUserRoles()
							.size());
					final var userRoleResponse = userResponse.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					Assertions.assertEquals(userRole.getId()
							.getValue()
							.toString(), userRoleResponse.getRoleId());
					Assertions.assertEquals(userRole.getTenantId()
							.getValue()
							.toString(), userRoleResponse.getTenantId());
				});
	}

	@Test
	void testUpdateSaleMemberWhenValidRequestThenReturnUserResponse() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var saleMember = userSupporter.createUser(tenant.getId(), DefaultSystemRole.SALE_MEMBER_ROLE,
				CreateUserCommand.builder()
						.email("salemember-002@vinta.com")
						.fullName("Full Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		final var request = UpdateUserRequest.builder()
				.email("salemember-1022@vinta.com")
				.fullName("Full Name")
				.phoneNumber("1234567890")
				.userStatus(UserStatus.ACTIVE)
				.build();

		// Act & Assert
		webClient.put()
				.uri(uri -> uri.path("/api/user/users/{userId}")
						.build(saleMember.getId()
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
				.expectBody(UserResponse.class)
				.consumeWith(response -> {
					final var userResponse = response.getResponseBody();
					Assertions.assertNotNull(userResponse);
					Assertions.assertEquals(UserType.BACK_OFFICE, userResponse.getUserType());
					Assertions.assertEquals(request.getEmail(), userResponse.getEmail());
					Assertions.assertEquals(request.getFullName(), userResponse.getFullName());
					Assertions.assertEquals(request.getPhoneNumber(), userResponse.getPhoneNumber());
					Assertions.assertEquals(1, userResponse.getUserRoles()
							.size());
					final var userRoleResponse = userResponse.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					final var saleMemberRole = saleMember.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					Assertions.assertEquals(saleMemberRole.getRoleId()
							.getValue()
							.toString(), userRoleResponse.getRoleId());
					Assertions.assertEquals(saleMemberRole.getTenantId()
							.getValue()
							.toString(), userRoleResponse.getTenantId());
				});
	}

	@Test
	void testQueryAllUsersWhenValidRequestThenReturnSuccessfully() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant2.com");

		final var tenantOwner = userSupporter.createUser(tenant.getId(), DefaultSystemRole.TENANT_OWNER_ROLE,
				CreateUserCommand.builder()
						.email("tenantOwner-1101@vinta.com")
						.fullName("tenantOwner Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		final var saleMember = userSupporter.createUser(tenant.getId(), DefaultSystemRole.SALE_MEMBER_ROLE,
				CreateUserCommand.builder()
						.email("salemanager-1102@vinta.com")
						.fullName("salemanager Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		final var saleManager = userSupporter.createUser(tenant.getId(), DefaultSystemRole.SALE_MEMBER_ROLE,
				CreateUserCommand.builder()
						.email("salemember-1103@vinta.com")
						.fullName("salemember Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		final var users = List.of(tenantOwner, saleMember, saleManager);
		final var request = QueryUserPaginationRequest.builder()
				.page(0)
				.size(10)
				.sortDirection("ASC")
				.sortFields(List.of("id"))
				.build();

		final Map<String, String> requestParamMap = new HashMap<>();
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

					uriBuilder.path("/api/user/users");
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
					final var userResponses = Optional.ofNullable(response.getResponseBody())
							.map(Paging::getContent)
							.map(ls -> (List<UserResponse>) ls.stream()
									.map(it -> objectMapper.convertValue(it, UserResponse.class))
									.toList())
							.orElseGet(List::of);
					Assertions.assertEquals(3, userResponses.size());

					userResponses.forEach(actualUser -> {
						final var expectedUser = users.stream()
								.filter(it -> it.getId()
										.getValue()
										.toString()
										.equals(actualUser.getId()))
								.findFirst()
								.orElseThrow();

						Assertions.assertNotNull(actualUser);
						Assertions.assertEquals(expectedUser.getUserType(), actualUser.getUserType());
						Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
						Assertions.assertEquals(expectedUser.getFullName(), actualUser.getFullName());
						Assertions.assertEquals(expectedUser.getPhoneNumber(), actualUser.getPhoneNumber());
						Assertions.assertEquals(1, actualUser.getUserRoles()
								.size());
						final var userRoleResponse = actualUser.getUserRoles()
								.stream()
								.toList()
								.getFirst();
						final var saleMemberRole = expectedUser.getUserRoles()
								.stream()
								.toList()
								.getFirst();
						Assertions.assertEquals(saleMemberRole.getRoleId()
								.getValue()
								.toString(), userRoleResponse.getRoleId());
						Assertions.assertEquals(saleMemberRole.getTenantId()
								.getValue()
								.toString(), userRoleResponse.getTenantId());

						Assertions.assertNotNull(actualUser.getCreatedAt());
						Assertions.assertNotNull(actualUser.getUpdatedAt());
					});

				});
	}

	@Test
	void testGetSaleMemberWhenValidRequestThenReturnUserResponse() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var saleMember = userSupporter.createUser(tenant.getId(), DefaultSystemRole.SALE_MEMBER_ROLE,
				CreateUserCommand.builder()
						.email("salemember-002@vinta.com")
						.fullName("Full Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		// Act & Assert
		webClient.get()
				.uri(uri -> uri.path("/api/user/users/{userId}")
						.build(saleMember.getId()
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
				.expectBody(UserResponse.class)
				.consumeWith(response -> {
					final var userResponse = response.getResponseBody();
					Assertions.assertNotNull(userResponse);
					Assertions.assertEquals(UserType.BACK_OFFICE, userResponse.getUserType());
					Assertions.assertEquals(saleMember.getEmail(), userResponse.getEmail());
					Assertions.assertEquals(saleMember.getFullName(), userResponse.getFullName());
					Assertions.assertEquals(saleMember.getPhoneNumber(), userResponse.getPhoneNumber());
					Assertions.assertEquals(1, userResponse.getUserRoles()
							.size());
					final var userRoleResponse = userResponse.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					final var saleMemberRole = saleMember.getUserRoles()
							.stream()
							.toList()
							.getFirst();
					Assertions.assertEquals(saleMemberRole.getRoleId()
							.getValue()
							.toString(), userRoleResponse.getRoleId());
					Assertions.assertEquals(saleMemberRole.getTenantId()
							.getValue()
							.toString(), userRoleResponse.getTenantId());
				});
	}

	@Test
	void testDeleteUsersWhenValidRequestThenReturnSuccessfully() {
		// Arrange
		final var tenant = tenantSupporter.initializeTenant("tenant1.com");
		final var saleMember = userSupporter.createUser(tenant.getId(), DefaultSystemRole.SALE_MEMBER_ROLE,
				CreateUserCommand.builder()
						.email("salemember-00333@vinta.com")
						.fullName("Full Name")
						.phoneNumber("1234567890")
						.userType(UserType.BACK_OFFICE)
						.build());

		final var request = DeleteUserRequest.builder()
				.byUserIds(Set.of(saleMember.getId()
						.getValue()))
				.build();

		final Map<String, String> requestParamMap = new HashMap<>();
		requestParamMap.put("byUserIds", request.getByUserIds()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining(",")));

		// Act & Assert
		webClient.delete()
				.uri(uriBuilder -> {
					final LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>(requestParamMap
							.entrySet()
							.stream()
							.filter(it -> StringUtils.isNotBlank(it.getValue()))
							.collect(Collectors.toMap(Map.Entry::getKey, it -> List.of(it.getValue()))));

					uriBuilder.path("/api/user/users");
					uriBuilder.queryParams(requestParams);
					return uriBuilder.build();
				})
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
					Assertions.assertTrue(userQueryService.findSingleUser(FilterUserQuery.builder()
							.byUserId(saleMember.getId())
							.build())
							.isEmpty());
				});
	}
}
