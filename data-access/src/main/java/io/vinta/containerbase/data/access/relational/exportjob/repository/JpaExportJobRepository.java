package io.vinta.containerbase.data.access.relational.exportjob.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.exportjob.entities.ExportJobEntity;

public interface JpaExportJobRepository extends BaseQuerydslRepository<ExportJobEntity, Long> {

}
