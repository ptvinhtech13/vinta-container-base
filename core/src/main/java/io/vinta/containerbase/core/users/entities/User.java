package io.vinta.containerbase.core.users.entities;

import io.vinta.containerbase.common.baseid.BaseEntity;
import io.vinta.containerbase.common.baseid.UserId;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.enums.UserType;
import io.vinta.containerbase.core.userrole.entities.UserRole;
import java.time.Instant;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@Builder
@With
@RequiredArgsConstructor
@ToString(of = { "id", "email", "fullName", "phoneNumber", "userStatus", "userType" })
public class User extends BaseEntity<UserId> {
	private final UserId id;
	private final UserType userType;
	private final UserStatus userStatus;
	private final String phoneNumber;
	private final String email;
	private final String fullName;
	private final String avatarPath; // S3 bucket: bucket_name:image/image.png

	private final Set<UserRole> userRoles;
	private final Instant createdAt;
	private final Instant updatedAt;
	private final UserId createdBy;
	private final UserId updatedBy;
}