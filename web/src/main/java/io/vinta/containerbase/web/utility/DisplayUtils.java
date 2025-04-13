package io.vinta.containerbase.web.utility;

import io.vinta.containerbase.core.importjob.entities.FileDataSource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

@UtilityClass
public class DisplayUtils {

	public static String formatSourcesDisplay(List<FileDataSource> sources) {
		if (sources == null || sources.isEmpty()) {
			return "";
		}
		return sources.stream()
				.map(FileDataSource::getPath)
				.map(FilenameUtils::getName)
				.collect(Collectors.joining(", "));
	}
}