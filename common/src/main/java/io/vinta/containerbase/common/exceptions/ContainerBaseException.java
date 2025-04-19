package io.vinta.containerbase.common.exceptions;

public class ContainerBaseException extends RuntimeException {
	public ContainerBaseException() {
	}

	public ContainerBaseException(String message) {
		super(message);
	}

	public ContainerBaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
