package io.vinta.containerbase.app.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "io.vinta.containerbase.system-user")
@RequiredArgsConstructor
public class SuperAdminConfigProperties {
	private String email;
	private String fullName;
	private String password;
}
