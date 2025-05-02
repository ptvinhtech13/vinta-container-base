package io.vinta.containerbase.data.access.relational.useraccess.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.useraccess.entities.UserAccessEntity;

public interface JpaUserAccessRepository extends BaseQuerydslRepository<UserAccessEntity, Long> {
}
