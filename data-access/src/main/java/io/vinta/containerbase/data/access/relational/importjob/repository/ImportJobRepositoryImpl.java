/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.data.access.relational.importjob.repository;

import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.data.access.relational.importjob.mapper.ImportJobEntityMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportJobRepositoryImpl implements ImportJobRepository {

	private final JpaImportJobRepository repository;

	@Override
	public ImportJob save(ImportJob job) {
		return ImportJobEntityMapper.INSTANCE.toModel(Optional.ofNullable(job.getId())
				.map(jobId -> {
					final var existing = repository.findById(jobId.getValue())
							.orElseGet(() -> (repository.save(ImportJobEntityMapper.INSTANCE.toNewEntity(job))));
					return repository.save(ImportJobEntityMapper.INSTANCE.toUpdateEntity(existing, job));
				})
				.orElseGet(() -> repository.save(ImportJobEntityMapper.INSTANCE.toNewEntity(job))));
	}
}
