package io.vinta.containerbase.data.access.relational.tenant.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.tenant.entities.TenantEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTenantRepository extends BaseQuerydslRepository<TenantEntity, Long> {
}
