package io.vinta.containerbase.common.exceptions.config;

import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import java.util.Map;
import java.util.Optional;

public record ErrorCodeConfig(Map<Long, ErrorCodeMessage> errorCodeMap) {

	public ErrorCodeMessage getMessage() {
		return getMessage(CommonErrorConstants.DEFAULT_ERROR);
	}

	public ErrorCodeMessage getMessage(long errorCode) {
		return Optional.of(errorCodeMap.get(errorCode))
				.orElse(errorCodeMap.get(CommonErrorConstants.DEFAULT_ERROR));
	}

	public ErrorCodeMessage getMessage(long errorCode, Object... args) {
		var errorCodeMessage = Optional.of(errorCodeMap.get(errorCode))
				.orElseGet(() -> errorCodeMap.get(CommonErrorConstants.DEFAULT_ERROR));
		return ErrorCodeMessage.builder()
				.code(errorCode)
				.message(String.format(errorCodeMessage.getMessage(), args))
				.build();
	}

	public ErrorCodeMessage internalServerErrorWithMessage(String errorMessage) {
		return ErrorCodeMessage.builder()
				.code(CommonErrorConstants.INTERNAL_SERVER_ERROR)
				.message(errorMessage)
				.build();
	}
}
