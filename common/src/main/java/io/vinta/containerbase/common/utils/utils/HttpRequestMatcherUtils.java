package io.vinta.containerbase.common.utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HttpRequestMatcherUtils {
	public static boolean isMatchPathRequest(String pattern, String path) {
		if (StringUtils.isBlank(pattern) || StringUtils.isBlank(path)) {
			throw new IllegalArgumentException(String.format("pattern: [%s]  or path: [%s] is empty", pattern, path));
		}
		final var pathPattern = new PathPatternParser().parse(pattern);

		if (log.isDebugEnabled()) {
			log.debug("Matching path: pattern: {} - path: {}", pattern, path);
		}
		return pathPattern.matches(PathContainer.parsePath(path));
	}
}
