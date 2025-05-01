package io.vinta.containerbase.common.exceptions.config;

import io.vinta.containerbase.common.exceptions.ContainerBaseException;
import io.vinta.containerbase.common.exceptions.InternalServerErrorException;
import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import io.vinta.containerbase.common.exceptions.models.ErrorResponse;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ErrorCodeConfig errorCodeConfig;

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		if (exception instanceof MethodArgumentNotValidException e) {
			return handleMethodArgumentNotValidException(e, request);
		}
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.httpStatusCode(statusCode)
				.build());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception,
			WebRequest request) {
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.build());
	}

	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataAccessException exception, WebRequest request) {
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.build());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception,
			WebRequest request) {
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception,
			WebRequest request) {
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.build());
	}

	private ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
			WebRequest request) {
		final var fieldErrors = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> ErrorResponse.ErrorField.builder()
						.field(error.getField())
						.message(error.getDefaultMessage())
						.rejectedValue(error.getRejectedValue())
						.build())
				.toList();

		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.customMessage("Validation Failed")
				.request(request)
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.errorFields(fieldErrors)
				.build());
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
		return handleAllExceptions(ErrorParams.builder()
				.exception(exception)
				.request(request)
				.build());
	}

	private ResponseEntity<Object> handleAllExceptions(ErrorParams params) {
		final var exception = params.getException();
		final var servletWebRequest = (ServletWebRequest) params.getRequest();
		final var path = servletWebRequest.getContextPath();

		if (log.isDebugEnabled()) {
			log.debug(String.format("Handling exception type %s on result %s", exception.getClass()
					.getSimpleName(), path), exception);
		}

		final var status = Optional.ofNullable(params.getHttpStatusCode())
				.orElseGet(() -> Optional.ofNullable(exception.getClass()
						.getAnnotation(ResponseStatus.class))
						.map(ResponseStatus::value)
						.orElse(HttpStatus.INTERNAL_SERVER_ERROR));

		var abstractVintaExceptionOptional = Optional.<ContainerBaseException>empty();
		var optionalData = Optional.empty();

		if (exception instanceof ContainerBaseException baseException) {
			log.error(exception.getMessage());
			abstractVintaExceptionOptional = Optional.of(baseException);
			optionalData = Optional.ofNullable(baseException.getOptionalData());

			baseException.setErrorCodeMessage(Optional.ofNullable(baseException.getErrorCodeMessage())
					.orElseGet(() -> ErrorCodeMessage.builder()
							.code(CommonErrorConstants.INTERNAL_SERVER_ERROR)
							.message(Optional.ofNullable(exception.getMessage())
									.orElseGet(exception::getLocalizedMessage))
							.build()));

		} else if (exception instanceof DataAccessException) {
			log.error(exception.getMessage(), exception);
			final var message = errorCodeConfig.getMessage(CommonErrorConstants.INTERNAL_SERVER_ERROR);
			abstractVintaExceptionOptional = Optional.of(new InternalServerErrorException(message));
		}

		final var error = abstractVintaExceptionOptional.map(ContainerBaseException::getErrorCodeMessage)
				.orElseGet(() -> {
					if (status.is5xxServerError()) {
						return ErrorCodeMessage.builder()
								.code(CommonErrorConstants.INTERNAL_SERVER_ERROR)
								.message(Optional.ofNullable(params.getCustomMessage())
										.orElseGet(() -> Optional.ofNullable(exception.getMessage())
												.orElseGet(exception::getLocalizedMessage)))
								.build();
					}
					return ErrorCodeMessage.builder()
							.code(CommonErrorConstants.BAD_REQUEST)
							.message(Optional.ofNullable(params.getCustomMessage())
									.orElseGet(() -> Optional.ofNullable(exception.getMessage())
											.orElseGet(exception::getLocalizedMessage)))
							.build();
				});

		if (CommonErrorConstants.INTERNAL_SERVER_ERROR == error.getCode()) {
			log.error(error.getMessage(), exception);
		}

		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.errorCode(error.getCode())
						.message(error.getMessage())
						.optionalData(optionalData)
						.path(path)
						.timestamp(Instant.now())
						.details(params.getErrorFields())
						.build());
	}

	private ContainerBaseException toInternalServerError(Exception exception) {
		log.error(exception.getMessage(), exception);
		return new ContainerBaseException(errorCodeConfig.getMessage(CommonErrorConstants.INTERNAL_SERVER_ERROR));
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ErrorParams {

		private Exception exception;
		private String customMessage;
		private WebRequest request;

		private HttpStatusCode httpStatusCode;
		private List<ErrorResponse.ErrorField> errorFields;

	}
}
