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
package io.vinta.containerbase.core.containers.service;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.core.containers.ContainerQueryService;
import io.vinta.containerbase.core.containers.ContainerRepository;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerQueryServiceImpl implements ContainerQueryService {

	private final ContainerRepository repository;

	@Override
	public Paging<Container> queryContainers(FindContainerQuery query) {

		return repository.queryContainers(query);
	}
}
