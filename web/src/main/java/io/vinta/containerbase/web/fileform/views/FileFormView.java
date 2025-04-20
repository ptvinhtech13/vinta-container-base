package io.vinta.containerbase.web.fileform.views;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FileFormView {
	private final String id;
	private final String fileFormName;
}
