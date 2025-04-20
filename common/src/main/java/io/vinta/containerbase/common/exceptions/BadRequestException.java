package io.vinta.containerbase.common.exceptions;

public class BadRequestException extends ContainerBaseException {
	public BadRequestException() {
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
