package io.vinta.containerbase.common.exceptions.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private long errorCode;
	private String message;
	private String path;
	private Instant timestamp;
	private Object optionalData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorField> details;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ErrorField {
		private String message;
		private String field;
		private Object rejectedValue;
	}
}
