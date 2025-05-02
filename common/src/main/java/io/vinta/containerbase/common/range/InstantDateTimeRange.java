package io.vinta.containerbase.common.range;

import java.time.Instant;
import lombok.Builder;

@Builder
public record InstantDateTimeRange(Instant from,
		Instant to) {
}
