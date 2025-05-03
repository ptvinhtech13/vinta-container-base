package io.vinta.containerbase.tests.config.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.images.ImagePullPolicy;
import org.testcontainers.utility.DockerImageName;

public class DoNotPullPolicy implements ImagePullPolicy {

	private static final Logger logger = LoggerFactory.getLogger(DoNotPullPolicy.class);

	@Override
	public boolean shouldPull(DockerImageName dockerImageName) {
		if (logger.isWarnEnabled()) {
			logger.warn("Do not pull image: {}, please pull the images before running tests", dockerImageName
					.asCanonicalNameString());
		}
		return false;
	}
}
