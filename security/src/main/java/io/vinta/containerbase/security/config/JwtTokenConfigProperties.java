package io.vinta.containerbase.security.config;

import io.vinta.containerbase.common.security.domains.JwtTokenType;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "io.vinta.containerbase.jwt-token")
public class JwtTokenConfigProperties {

	private String issuer;
	private String privateKey;
	private String publicKey;
	private List<TokenConfig> tokenConfig;

	@Getter
	@Setter
	public static class TokenConfig {
		private JwtTokenType tokenType;
		private Duration timeToLive;
	}
}
