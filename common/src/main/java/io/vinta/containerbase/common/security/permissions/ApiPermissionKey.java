package io.vinta.containerbase.common.security.permissions;

import io.vinta.containerbase.common.baseid.FeatureNodeId;
import io.vinta.containerbase.common.enums.UserType;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;

@Getter
public enum ApiPermissionKey {
	TENANT_MGMT(1_1001L, "root.tenant", FeatureNodeType.MODULE, "Tenant Management", 0, UserType.SYSTEM_ADMIN),
	TENANT_MGMT_VIEW(1_1002L, "root.tenant.view", FeatureNodeType.API, "View Tenant", 1, UserType.SYSTEM_ADMIN),
	TENANT_MGMT_CREATE(1_1003L, "root.tenant.add", FeatureNodeType.API, "Create New Tenant", 2, UserType.SYSTEM_ADMIN),
	TENANT_MGMT_UPDATE(1_1004L, "root.tenant.update", FeatureNodeType.API, "Create New Tenant", 2,
			UserType.SYSTEM_ADMIN),

	USER_MGMT(2_1001L, "root.user", FeatureNodeType.MODULE, "User Management", 1, UserType.SYSTEM_ADMIN,
			UserType.BACK_OFFICE),
	USER_MGMT_VIEW(2_1002L, "root.user.view", FeatureNodeType.API, "View User List", 1, UserType.SYSTEM_ADMIN,
			UserType.BACK_OFFICE),
	USER_MGMT_ADD(2_1003L, "root.user.add", FeatureNodeType.API, "Add new user", 2, UserType.SYSTEM_ADMIN,
			UserType.BACK_OFFICE),
	USER_MGMT_DELETE(2_1004L, "root.user.delete", FeatureNodeType.API, "Delete User", 3, UserType.SYSTEM_ADMIN,
			UserType.BACK_OFFICE),
	USER_MGMT_UPDATE(2_1005L, "root.user.update", FeatureNodeType.API, "Update User", 4, UserType.SYSTEM_ADMIN,
			UserType.BACK_OFFICE),

	ROLE_MGMT(3_1001L, "root.role", FeatureNodeType.MODULE, "Role Management", 2, UserType.SYSTEM_ADMIN),
	ROLE_MGMT_VIEW(3_1002L, "root.role.view", FeatureNodeType.API, "View Role List", 1, UserType.SYSTEM_ADMIN),
	ROLE_MGMT_ADD(3_1003L, "root.role.add", FeatureNodeType.API, "Add new role", 2, UserType.SYSTEM_ADMIN),
	ROLE_MGMT_DELETE(3_1004L, "root.role.delete", FeatureNodeType.API, "Delete Role", 3, UserType.SYSTEM_ADMIN),
	ROLE_MGMT_UPDATE(3_1005L, "root.role.update", FeatureNodeType.API, "Update Role", 4, UserType.SYSTEM_ADMIN),

	//--END--

	;

	private final FeatureNodeId id;
	private final String nodeTitle;
	private final String nodePath;
	private final FeatureNodeType nodeType;
	private final int displayOrder;
	private final Set<UserType> allowedUserTypes;

	ApiPermissionKey(Long id, String nodePath, FeatureNodeType nodeType, String nodeTitle, int displayOrder,
			UserType... allowedUserTypes) {
		this.id = new FeatureNodeId(id);
		this.nodePath = nodePath;
		this.nodeType = nodeType;
		this.nodeTitle = nodeTitle;
		this.displayOrder = displayOrder;
		this.allowedUserTypes = Optional.ofNullable(allowedUserTypes)
				.map(Set::of)
				.orElseGet(Set::of);
	}

}