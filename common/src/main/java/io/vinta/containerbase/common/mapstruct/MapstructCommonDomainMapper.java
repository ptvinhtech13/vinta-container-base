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
package io.vinta.containerbase.common.mapstruct;

import io.vinta.containerbase.common.baseid.BaseId;
import io.vinta.containerbase.common.baseid.ContainerId;
import io.vinta.containerbase.common.baseid.ImportJobId;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class)
public interface MapstructCommonDomainMapper {

	MapstructCommonDomainMapper INSTANCE = Mappers.getMapper(MapstructCommonDomainMapper.class);

	@Named("stringToImportJobId")
	default ImportJobId stringToImportJobId(String source) {
		return Optional.ofNullable(source)
				.map(ImportJobId::new)
				.orElse(null);
	}

	@Named("importJobIdToString")
	default String importJobIdToString(ImportJobId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("stringToContainerId")
	default ContainerId stringToContainerId(String source) {
		return Optional.ofNullable(source)
				.map(Long::valueOf)
				.map(ContainerId::new)
				.orElse(null);
	}

	@Named("longToContainerId")
	default ContainerId longToContainerId(Long source) {
		return Optional.ofNullable(source)
				.map(ContainerId::new)
				.orElse(null);
	}

	@Named("containerIdToString")
	default String containerIdToString(ContainerId source) {
		return Optional.ofNullable(source)
				.map(BaseId::getValue)
				.map(String::valueOf)
				.orElse(null);
	}

}
