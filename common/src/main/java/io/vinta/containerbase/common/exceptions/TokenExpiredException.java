package io.vinta.containerbase.common.exceptions;

import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends ContainerBaseException {
	public TokenExpiredException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	}

	public TokenExpiredException(ErrorCodeMessage errorCodeMessage, Object optionalData) {
		super(errorCodeMessage, optionalData);
	}

	public TokenExpiredException(String message) {
		super(message);
	}

	public TokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenExpiredException(Throwable cause) {
		super(cause);
	}

	public TokenExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
