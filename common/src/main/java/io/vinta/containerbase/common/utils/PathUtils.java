package io.vinta.containerbase.common.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class PathUtils {
	private static final Pattern WINDOWS_DRIVE_PATTERN = Pattern.compile("^[a-zA-Z]:\\\\.*");
	private static final Pattern URL_PATTERN = Pattern.compile("http(s)?://[^/]+");

	public static boolean isValidAbsolutePath(String path) {
		if (path == null || path.isEmpty()) {
			return false;
		}

		Path p = Paths.get(path)
				.normalize();
		boolean isAbsolute = p.isAbsolute();
		boolean isWindowsAbsolute = WINDOWS_DRIVE_PATTERN.matcher(path)
				.matches();
		boolean isUnixAbsolute = path.startsWith("/");
		boolean hasPrefix = FilenameUtils.getPrefixLength(path) > 0;
		return isAbsolute || isWindowsAbsolute || isUnixAbsolute || hasPrefix;
	}

	public static String maskingURL(String message) {
		if (StringUtils.isBlank(message)) {
			return message;
		}
		final var matcher = URL_PATTERN.matcher(message);
		return matcher.replaceAll("[MASKED]");
	}
}
