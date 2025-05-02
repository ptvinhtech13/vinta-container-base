package io.vinta.containerbase.data.access.relational.userrole.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.userrole.entities.UserRoleEntity;
import io.vinta.containerbase.data.access.relational.userrole.entities.UserRoleId;

public interface JpaUserRoleRepository extends BaseQuerydslRepository<UserRoleEntity, UserRoleId> {
}
