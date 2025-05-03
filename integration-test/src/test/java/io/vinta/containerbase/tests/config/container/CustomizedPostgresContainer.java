package io.vinta.containerbase.tests.config.container;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;

public class CustomizedPostgresContainer<SELF extends PostgreSQLContainer<SELF>> extends PostgreSQLContainer<SELF> {

	private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("postgres");

	private static final DockerImageName PGVECTOR_IMAGE_NAME = DockerImageName.parse("pgvector/pgvector");

	private static final String FSYNC_OFF_OPTION = "fsync=off";

	public CustomizedPostgresContainer(final DockerImageName dockerImageName) {
		super(dockerImageName);
		dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME, PGVECTOR_IMAGE_NAME);

		this.waitStrategy = new LogMessageWaitStrategy().withRegEx(
				".*database system is ready to accept connections.*\\s")
				.withTimes(2)
				.withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS));
		this.setCommand("postgres", "-c", FSYNC_OFF_OPTION);
		this.setCommand("postgres", "-c", "max_connections=100000");

		addExposedPort(POSTGRESQL_PORT);
	}
}
