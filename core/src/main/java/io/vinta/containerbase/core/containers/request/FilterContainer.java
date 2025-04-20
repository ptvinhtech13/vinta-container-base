package io.vinta.containerbase.core.containers.request;

import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FilterContainer {
	private Instant byCreatedFrom;
	private Instant byCreatedTo;
	private String byImportJobId;
	private Set<String> byContainerNumbers;
	private String byBookingReference;
	private String byOwnerShippingLineCode;
}
