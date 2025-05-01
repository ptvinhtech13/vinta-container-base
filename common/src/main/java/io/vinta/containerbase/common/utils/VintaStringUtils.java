package io.vinta.containerbase.common.utils;

import java.text.Normalizer;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class VintaStringUtils {

	private static final Pattern PATTERN_STRIP_DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

	public static String removeHyphen(final UUID uuid) {
		return uuid.toString()
				.replaceAll("-", "");
	}

	public static String replaceParams(final String template, final Map<String, String> map) {
		return map.entrySet()
				.stream()
				.reduce(template, (s, e) -> s.replace("${" + e.getKey()
						+ "}", e.getValue()), (s, s2) -> s);
	}

	public static String removeDiacritics(String input) {
		if (StringUtils.isEmpty(input)) {
			return input;
		}

		String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);

		return PATTERN_STRIP_DIACRITICS.matcher(normalizedString)
				.replaceAll("");
	}
}
