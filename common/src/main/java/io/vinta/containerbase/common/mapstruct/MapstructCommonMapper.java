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

import io.vinta.containerbase.common.utils.PathUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class)
public interface MapstructCommonMapper {

	MapstructCommonMapper INSTANCE = Mappers.getMapper(MapstructCommonMapper.class);

	@Named("bigDecimalToString")
	default String bigDecimalToString(BigDecimal source) {
		return Optional.ofNullable(source)
				.map(BigDecimal::stripTrailingZeros)
				.map(BigDecimal::toPlainString)
				.orElse(null);
	}

	@Named("stringToBigDecimal")
	default BigDecimal stringToBigDecimal(String value) {
		return StringUtils.isNotBlank(value) ? new BigDecimal(value) : null;
	}

	@Named("stringToTimeZone")
	default TimeZone stringToTimeZone(String timeZone) {
		return StringUtils.isNotBlank(timeZone) ? TimeZone.getTimeZone(timeZone) : null;
	}

	@Named("timeZoneToString")
	default String timeZoneToString(TimeZone timeZone) {
		return Optional.ofNullable(timeZone)
				.map(TimeZone::getID)
				.orElse(null);
	}

	@Named("longToInstant")
	default Instant longToInstant(Long source) {
		return Optional.ofNullable(source)
				.map(Instant::ofEpochMilli)
				.orElse(null);
	}

	@Named("longToString")
	default String longToString(Long source) {
		return Optional.ofNullable(source)
				.map(String::valueOf)
				.orElse(null);
	}

	@Named("instantToLong")
	default Long instantToLong(Instant source) {
		return Optional.ofNullable(source)
				.map(Instant::toEpochMilli)
				.orElse(null);
	}

	@Named("zonedDateTimeToLong")
	default Long zonedDateTimeToLong(ZonedDateTime source) {
		return Optional.ofNullable(source)
				.map(ZonedDateTime::toInstant)
				.map(Instant::toEpochMilli)
				.orElse(null);
	}

	@Named("longToZeroIfNull")
	default Long longToZeroIfNull(Long source) {
		return Optional.ofNullable(source)
				.orElse(0L);
	}

	@Named("bigDecimalToZeroIfNull")
	default BigDecimal bigDecimalToZeroIfNull(BigDecimal source) {
		return Optional.ofNullable(source)
				.orElse(BigDecimal.ZERO);
	}

	@Named("stringToNullIfEmpty")
	default String stringToNullIfEmpty(String source) {
		return Optional.ofNullable(source)
				.filter(StringUtils::isNotBlank)
				.orElse(null);
	}

	@Named("stringToClearance")
	default String stringToClearance(String source) {
		return Optional.ofNullable(source)
				.filter(StringUtils::isNotBlank)
				.map(String::trim)
				.orElse(null);
	}

	@Named("stringToMaskedUrl")
	default String stringToMaskedUrl(String source) {
		return Optional.ofNullable(source)
				.filter(StringUtils::isNotBlank)
				.map(PathUtils::maskingURL)
				.orElse(null);
	}

}
