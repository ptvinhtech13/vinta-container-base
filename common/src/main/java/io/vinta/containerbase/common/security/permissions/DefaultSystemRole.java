package io.vinta.containerbase.common.security.permissions;

import java.util.Optional;
import java.util.Set;
import lombok.Getter;

@Getter
public enum DefaultSystemRole {
	SYSTEM_ADMIN_ROLE("SYSTEM_ADMIN", "System Admin", true),

	TENANT_OWNER_ROLE("TENANT_OWNER", "Tenant Owner", false, PacificApiPermissionKey.USER_MGMT,
			PacificApiPermissionKey.ROLE_MGMT),

	SALE_MANAGER_ROLE("SALE_MANAGER_ROLE", "Sale Manager", false, PacificApiPermissionKey.USER_MGMT,
			PacificApiPermissionKey.ROLE_MGMT),

	SALE_MEMBER_ROLE("SALE_MEMBER_ROLE", "Sale Member", false, PacificApiPermissionKey.TENANT_MGMT_VIEW);

	private final String roleKey;
	private final String roleTitle;
	private final boolean isDefaultSystemTenant;
	private final Set<PacificApiPermissionKey> allowedPermissions;

	DefaultSystemRole(String roleKey, String roleTitle, boolean isDefaultSystemTenant,
			PacificApiPermissionKey... allowedPermissions) {
		this.roleKey = roleKey;
		this.roleTitle = roleTitle;
		this.isDefaultSystemTenant = isDefaultSystemTenant;
		this.allowedPermissions = Optional.ofNullable(allowedPermissions)
				.map(Set::of)
				.orElseGet(Set::of);
	}
}
