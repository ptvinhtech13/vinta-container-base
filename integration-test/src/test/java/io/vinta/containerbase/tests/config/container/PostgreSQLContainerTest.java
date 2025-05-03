package io.vinta.containerbase.tests.config.container;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PostgreSQLContainerTest {

	@ServiceConnection
	static final PostgreSQLContainer<?> postgreSQLContainer = new CustomizedPostgresContainer<>(DockerImageName.parse(
			"postgres:16.3-alpine"));

	static {
		postgreSQLContainer.start();
	}

	@DynamicPropertySource
	static void registerKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

}
