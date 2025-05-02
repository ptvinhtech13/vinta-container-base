package io.vinta.containerbase.core.role.request;

import io.vinta.containerbase.common.baseid.RoleId;
import io.vinta.containerbase.common.baseid.TenantId;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class FilterRoleQuery {
	private TenantId byTenantId;
	private Set<RoleId> byRoleIds;
}
