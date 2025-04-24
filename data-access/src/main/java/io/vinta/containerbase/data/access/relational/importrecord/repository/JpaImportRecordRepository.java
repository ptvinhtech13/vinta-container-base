package io.vinta.containerbase.data.access.relational.importrecord.repository;

import io.vinta.containerbase.common.querydsl.BaseQuerydslRepository;
import io.vinta.containerbase.data.access.relational.importrecord.entities.ImportRecordEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaImportRecordRepository extends BaseQuerydslRepository<ImportRecordEntity, Long> {

	@Modifying
	@Transactional
	@Query("delete from ImportRecordEntity where importJobId = :importJobId")
	void deleteAllByImportJobId(@Param("importJobId") String importJobId);
}
