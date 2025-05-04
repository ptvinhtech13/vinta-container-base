package io.vinta.containerbase.data.access.relational.users.repository;

import com.querydsl.core.types.Predicate;
import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.users.entities.QUserEntity;
import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserRepository extends BaseQuerydslRepository<UserEntity, Long> {

	@Modifying
	@Query("UPDATE UserEntity e " + "SET "
			+ "e.deletedAt = current_timestamp,"
			+ "e.userStatus = 'DELETED'"
			+ "WHERE EXISTS (SELECT ur FROM UserRoleEntity ur WHERE ur.user = e AND ur.tenantId = :tenantId) "
			+ "AND e.id IN :userIds")
	void deleteUserByTenantIdAndUserIds(@Param("tenantId") Long tenantId, @Param("userIds") List<Long> userIds);

	@Override
	default Predicate softDeletionPredicate() {
		return QUserEntity.userEntity.deletedAt.isNull();
	}
}
