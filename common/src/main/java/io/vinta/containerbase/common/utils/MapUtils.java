package io.vinta.containerbase.common.utils;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapUtils {

	public static Map<String, Object> stripNullValue(Map<String, Object> convertValue) {
		convertValue.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == null)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet())
				.forEach(convertValue::remove);
		return convertValue;
	}
}
