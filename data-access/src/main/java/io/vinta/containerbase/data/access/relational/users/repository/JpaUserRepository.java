package io.vinta.containerbase.data.access.relational.users.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.users.entities.UserEntity;

public interface JpaUserRepository extends BaseQuerydslRepository<UserEntity, Long> {
}
