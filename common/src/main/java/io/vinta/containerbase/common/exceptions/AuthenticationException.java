package io.vinta.containerbase.common.exceptions;

import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationException extends ContainerBaseException {

	public AuthenticationException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	}

	public AuthenticationException(ErrorCodeMessage errorCodeMessage, Object optionalData) {
		super(errorCodeMessage, optionalData);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
