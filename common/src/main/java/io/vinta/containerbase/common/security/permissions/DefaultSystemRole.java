/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.common.security.permissions;

import java.util.Optional;
import java.util.Set;
import lombok.Getter;

@Getter
public enum DefaultSystemRole {
	SYSTEM_ADMIN_ROLE(1L, "SYSTEM_ADMIN", "System Admin", true),

	TENANT_OWNER_ROLE(null, "TENANT_OWNER", "Tenant Owner", false, PacificApiPermissionKey.USER_MGMT,
			PacificApiPermissionKey.ROLE_MGMT),

	SALE_MANAGER_ROLE(null, "SALE_MANAGER_ROLE", "Sale Manager", false, PacificApiPermissionKey.USER_MGMT,
			PacificApiPermissionKey.ROLE_MGMT),

	SALE_MEMBER_ROLE(null, "SALE_MEMBER_ROLE", "Sale Member", false, PacificApiPermissionKey.TENANT_MGMT_VIEW);

	private final Long fixedRoleId;
	private final String roleKey;
	private final String roleTitle;
	private final boolean isDefaultSystemTenant;
	private final Set<PacificApiPermissionKey> allowedPermissions;

	DefaultSystemRole(Long roleId, String roleKey, String roleTitle, boolean isDefaultSystemTenant,
			PacificApiPermissionKey... allowedPermissions) {
		this.fixedRoleId = roleId;
		this.roleKey = roleKey;
		this.roleTitle = roleTitle;
		this.isDefaultSystemTenant = isDefaultSystemTenant;
		this.allowedPermissions = Optional.ofNullable(allowedPermissions)
				.map(Set::of)
				.orElseGet(Set::of);
	}
}
