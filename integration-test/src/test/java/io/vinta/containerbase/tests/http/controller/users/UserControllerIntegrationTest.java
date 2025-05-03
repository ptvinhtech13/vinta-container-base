package io.vinta.containerbase.tests.http.controller.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.app.users.SuperAdminConfigProperties;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.common.security.permissions.DefaultSystemRole;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.role.request.FilterRoleQuery;
import io.vinta.containerbase.core.role.request.RolePaginationQuery;
import io.vinta.containerbase.core.tenant.TenantCommandService;
import io.vinta.containerbase.core.tenant.TenantQueryService;
import io.vinta.containerbase.core.useraccess.UserTokenAccessService;
import io.vinta.containerbase.rest.user.request.CreateUserAccessBasicAuthRequest;
import io.vinta.containerbase.rest.user.request.CreateUserRequest;
import io.vinta.containerbase.rest.user.request.CreateUserRoleRequest;
import io.vinta.containerbase.rest.user.response.UserResponse;
import io.vinta.containerbase.tests.commons.supporter.AccessTokenSupporter;
import io.vinta.containerbase.tests.commons.supporter.TenantInitializationIntegrationSupporter;
import io.vinta.containerbase.tests.commons.utils.GenerateHttpHeader;
import io.vinta.containerbase.tests.commons.utils.HeaderGenerator;
import io.vinta.containerbase.tests.config.BaseWebClientWithDbTest;
import io.vinta.containerbase.tests.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
class UserControllerIntegrationTest extends BaseWebClientWithDbTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TenantCommandService tenantCommandService;

	@Autowired
	private TenantQueryService tenantQueryService;

	@Autowired
	private RoleQueryService roleQueryService;

	@Autowired
	private UserTokenAccessService userTokenAccessService;

	@Autowired
	private SuperAdminConfigProperties superAdminConfigProperties;

	private TenantInitializationIntegrationSupporter tenantSupporter;
	private AccessTokenSupporter accessTokenSupporter;

	@BeforeEach
	void setUp() {
		tenantSupporter = new TenantInitializationIntegrationSupporter(tenantCommandService, tenantQueryService);
		accessTokenSupporter = new AccessTokenSupporter(superAdminConfigProperties, userTokenAccessService);
	}

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

}
