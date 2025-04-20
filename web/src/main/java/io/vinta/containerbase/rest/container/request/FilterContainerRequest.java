package io.vinta.containerbase.rest.container.request;

import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FilterContainerRequest {
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
	private String byImportJobId;
	private Set<String> byContainerNumbers;
	private String byBookingReference;
	private String byOwnerShippingLineCode;
}
