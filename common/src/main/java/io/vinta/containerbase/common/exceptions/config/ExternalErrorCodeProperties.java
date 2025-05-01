package io.vinta.containerbase.common.exceptions.config;

import io.vinta.containerbase.common.exceptions.models.ErrorCodeMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "io.vinta.containerbase.error-codes")
@Component
@Data
public class ExternalErrorCodeProperties {

	private List<ErrorCodeMessage> errorCodes = new ArrayList<>();

	private List<String> errorCodePaths = new ArrayList<>();
}
