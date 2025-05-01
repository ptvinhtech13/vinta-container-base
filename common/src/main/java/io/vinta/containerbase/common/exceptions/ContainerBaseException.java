package io.vinta.containerbase.common.exceptions;

import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainerBaseException extends RuntimeException {
	private ErrorCodeMessage errorCodeMessage;
	private Object optionalData;

	public ContainerBaseException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage.getMessage());
		this.errorCodeMessage = errorCodeMessage;
	}

	public ContainerBaseException(ErrorCodeMessage errorCodeMessage, Object optionalData) {
		super(errorCodeMessage.getMessage());
		this.errorCodeMessage = errorCodeMessage;
		this.optionalData = optionalData;
	}

	protected ContainerBaseException(ErrorCodeMessage errorCodeMessage, Throwable cause) {
		super(errorCodeMessage.getMessage(), cause);
		this.errorCodeMessage = errorCodeMessage;
	}

	protected ContainerBaseException(ErrorCodeMessage errorCodeMessage, Throwable cause, Object optionalData) {
		super(errorCodeMessage.getMessage(), cause);
		this.errorCodeMessage = errorCodeMessage;
		this.optionalData = optionalData;
	}

	protected ContainerBaseException(ErrorCodeMessage errorCodeMessage, String message) {
		super(message);
		this.errorCodeMessage = errorCodeMessage;
	}

	protected ContainerBaseException(ErrorCodeMessage errorCodeMessage, String message, Object optionalData) {
		super(message);
		this.errorCodeMessage = errorCodeMessage;
		this.optionalData = optionalData;
	}

	protected ContainerBaseException(String message) {
		super(message);
	}

	protected ContainerBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContainerBaseException(Throwable cause) {
		super(cause);
	}

	public ContainerBaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
