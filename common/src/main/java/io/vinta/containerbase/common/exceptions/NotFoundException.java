package io.vinta.containerbase.common.exceptions;

import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ContainerBaseException {

	public NotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	}

	public NotFoundException(ErrorCodeMessage errorCodeMessage, Object optionalData) {
		super(errorCodeMessage, optionalData);
	}

	protected NotFoundException(ErrorCodeMessage errorCodeMessage, Throwable cause) {
		super(errorCodeMessage, cause);
	}

	protected NotFoundException(ErrorCodeMessage errorCodeMessage, Throwable cause, Object optionalData) {
		super(errorCodeMessage, cause, optionalData);
	}

	protected NotFoundException(ErrorCodeMessage errorCodeMessage, String message) {
		super(errorCodeMessage, message);
	}

	protected NotFoundException(ErrorCodeMessage errorCodeMessage, String message, Object optionalData) {
		super(errorCodeMessage, message, optionalData);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
