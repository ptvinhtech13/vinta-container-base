package io.vinta.containerbase.data.access.relational.role.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.role.entities.RoleEntity;

public interface JpaRoleRepository extends BaseQuerydslRepository<RoleEntity, Long> {
}
