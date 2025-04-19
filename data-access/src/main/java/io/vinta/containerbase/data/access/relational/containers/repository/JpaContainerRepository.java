package io.vinta.containerbase.data.access.relational.containers.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.containers.entities.ContainerEntity;

public interface JpaContainerRepository extends BaseQuerydslRepository<ContainerEntity, Long> {
}
