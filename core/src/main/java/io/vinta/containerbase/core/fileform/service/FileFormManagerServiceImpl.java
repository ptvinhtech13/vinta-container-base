package io.vinta.containerbase.core.fileform.service;

import io.vinta.containerbase.common.baseid.FileFormId;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import io.vinta.containerbase.core.fileform.entities.FileForm;
import io.vinta.containerbase.core.inout.FileFormExporter;
import io.vinta.containerbase.core.inout.FileFormImporter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileFormManagerServiceImpl implements FileFormManagerService {

	@Lazy
	@Autowired
	private List<FileFormExporter> exporters;

	@Lazy
	@Autowired
	private List<FileFormImporter> importers;

	private final Map<FileFormId, FileForm> fileFormMap = new ConcurrentHashMap<>();

	@Override
	public void init(List<FileForm> fileForms) {
		fileFormMap.putAll(fileForms.stream()
				.collect(Collectors.toMap(FileForm::getId, Function.identity())));
	}

	@Override
	public Optional<FileForm> getFileForm(FileFormId id) {
		return Optional.ofNullable(fileFormMap.get(id));

	}

	@Override
	public Optional<FileFormExporter> getExporter(FileFormId id) {
		return exporters.stream()
				.filter(it -> it.hasSupport(id))
				.findFirst();
	}

}
