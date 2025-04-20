package io.vinta.containerbase.rest.export.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FileFormResponse {
	private final String id;
	private final String fileFormName;
}
