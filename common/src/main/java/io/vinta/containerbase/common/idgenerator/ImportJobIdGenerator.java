package io.vinta.containerbase.common.idgenerator;

import io.vinta.containerbase.common.baseid.ImportJobId;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImportJobIdGenerator {

	public static ImportJobId randomId() {
		return new ImportJobId(UUID.randomUUID()
				.toString()
				.substring(0, 8)
				.toUpperCase());
	}
}
