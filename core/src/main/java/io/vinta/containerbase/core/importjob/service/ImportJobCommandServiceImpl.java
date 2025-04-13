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
package io.vinta.containerbase.core.importjob.service;

import io.vinta.containerbase.core.importjob.ImportJobCommandService;
import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.mapper.ImportJobMapper;
import io.vinta.containerbase.core.importjob.request.CreateImportJobCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportJobCommandServiceImpl implements ImportJobCommandService {
	private final ImportJobRepository repository;

	@Override
	public ImportJob createImportJob(CreateImportJobCommand command) {
		return repository.save(ImportJobMapper.INSTANCE.toEntity(command));
	}
}
