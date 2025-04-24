package io.vinta.containerbase.core.inout.models;

import java.util.Map;
import java.util.StringJoiner;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@With
public class RowValidationResult {
	@Builder.Default
	private final boolean isValid = false;
	private final String errorMessage;
	private final Map<String, String> modelData;

	@Override
	public String toString() {
		return new StringJoiner(", ", RowValidationResult.class.getSimpleName() + "[", "]").add("isValid=" + isValid)
				.add("errorMessage='" + errorMessage
						+ "'")
				.add("modelData=" + modelData)
				.toString();
	}
}
