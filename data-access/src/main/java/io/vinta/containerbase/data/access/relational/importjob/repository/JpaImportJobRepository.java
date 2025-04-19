package io.vinta.containerbase.data.access.relational.importjob.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.importjob.entities.ImportJobEntity;

public interface JpaImportJobRepository extends BaseQuerydslRepository<ImportJobEntity, String> {
}
