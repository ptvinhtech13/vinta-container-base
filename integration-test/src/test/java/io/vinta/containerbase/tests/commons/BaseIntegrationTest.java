package io.vinta.containerbase.tests.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.app.users.SuperAdminConfigProperties;
import io.vinta.containerbase.core.featurenodes.FeatureNodeQueryService;
import io.vinta.containerbase.core.role.RoleCommandService;
import io.vinta.containerbase.core.role.RoleQueryService;
import io.vinta.containerbase.core.tenant.TenantCommandService;
import io.vinta.containerbase.core.tenant.TenantQueryService;
import io.vinta.containerbase.core.useraccess.UserTokenAccessService;
import io.vinta.containerbase.core.users.UserCommandService;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.tests.commons.supporter.AccessTokenSupporter;
import io.vinta.containerbase.tests.commons.supporter.RoleInitializationIntegrationSupporter;
import io.vinta.containerbase.tests.commons.supporter.TenantInitializationIntegrationSupporter;
import io.vinta.containerbase.tests.commons.supporter.UserInitializationIntegrationSupporter;
import io.vinta.containerbase.tests.config.BaseWebClientWithDbTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseIntegrationTest extends BaseWebClientWithDbTest {
	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected TenantCommandService tenantCommandService;

	@Autowired
	protected TenantQueryService tenantQueryService;

	@Autowired
	protected RoleQueryService roleQueryService;

	@Autowired
	protected UserTokenAccessService userTokenAccessService;

	@Autowired
	protected UserCommandService userCommandService;

	@Autowired
	protected UserQueryService userQueryService;

	@Autowired
	protected RoleCommandService roleCommandService;

	@Autowired
	protected FeatureNodeQueryService featureNodeQueryService;

	@Autowired
	protected SuperAdminConfigProperties superAdminConfigProperties;

	protected TenantInitializationIntegrationSupporter tenantSupporter;
	protected AccessTokenSupporter accessTokenSupporter;
	protected UserInitializationIntegrationSupporter userSupporter;
	protected RoleInitializationIntegrationSupporter roleSupporter;

	@BeforeEach
	void setUp() {
		tenantSupporter = new TenantInitializationIntegrationSupporter(tenantCommandService, tenantQueryService);
		userSupporter = new UserInitializationIntegrationSupporter(userCommandService, roleQueryService);
		accessTokenSupporter = new AccessTokenSupporter(superAdminConfigProperties, userTokenAccessService);
		roleSupporter = new RoleInitializationIntegrationSupporter(roleCommandService);
	}
}
