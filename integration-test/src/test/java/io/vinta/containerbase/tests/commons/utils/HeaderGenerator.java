package io.vinta.containerbase.tests.commons.utils;

import io.vinta.containerbase.common.constants.SecurityConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HeaderGenerator {

	public static HttpHeaders generateHttpHeaders(GenerateHttpHeader template) {
		final var map = new HashMap<String, List<String>>();

		Optional.ofNullable(template.accessToken())
				.ifPresent(accessToken -> {
					map.put(HttpHeaders.AUTHORIZATION, List.of(SecurityConstants.BEARER + " "
							+ accessToken));
				});

		Optional.ofNullable(template.requestId())
				.ifPresent(id -> map.put(SecurityConstants.X_REQUEST_ID, List.of(String.valueOf(id))));
		Optional.ofNullable(template.tenantId())
				.ifPresent(id -> map.put(SecurityConstants.X_TENANT_ID, List.of(String.valueOf(id))));

		return Optional.ofNullable(template.header())
				.map(header -> {
					header.addAll(new LinkedMultiValueMap<>(map));
					return header;
				})
				.orElseGet(() -> new HttpHeaders(new LinkedMultiValueMap<>(map)));
	}
}
