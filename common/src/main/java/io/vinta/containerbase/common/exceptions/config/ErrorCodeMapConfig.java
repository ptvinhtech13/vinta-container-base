package io.vinta.containerbase.common.exceptions.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ErrorCodeMapConfig {

	public ExternalErrorCodeProperties loadCommonErrorCode() {
		final var commonErrorPath = "error-codes/error-codes-commons.yml";
		final var inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(commonErrorPath);
		try {
			return yamlMapper().readValue(inputStream, ExternalErrorCodeProperties.class);
		} catch (Exception ex) {
			log.error("Loading Common Error Code has failed.", ex);
			return null;
		}
	}

	public ObjectMapper yamlMapper() {
		final var objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper;
	}

	@Bean("errorCodeConfig")
	public ErrorCodeConfig getErrorCodeConfig(ExternalErrorCodeProperties externalErrorCodeProperties) {
		return new ErrorCodeConfig(Stream.concat(Optional.ofNullable(externalErrorCodeProperties.getErrorCodes())
				.orElseGet(Collections::emptyList)
				.stream(), Optional.ofNullable(loadCommonErrorCode())
						.map(ExternalErrorCodeProperties::getErrorCodes)
						.orElseGet(Collections::emptyList)
						.stream())
				.collect(Collectors.toUnmodifiableMap(ErrorCodeMessage::getCode, Function.identity())));
	}
}
