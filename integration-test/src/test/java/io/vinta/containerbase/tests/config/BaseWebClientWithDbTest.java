package io.vinta.containerbase.tests.config;

import io.vinta.containerbase.tests.config.container.PostgreSQLContainerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "PT60S")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseWebClientWithDbTest extends PostgreSQLContainerTest {

	@Autowired
	protected WebTestClient webClient;

}
