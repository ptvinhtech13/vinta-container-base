package io.vinta.containerbase.data.access.relational;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.vinta.containerbase.common.querydsl.ExtendedPostgresTemplates;
import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "io.vinta.containerbase.data.access.relational" })
@EntityScan(basePackages = { "io.vinta.containerbase.data.access.relational" })
@EnableJpaAuditing
@Slf4j
@RequiredArgsConstructor
public class ContainerBaseDataAccessConfiguration {

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return () -> AppSecurityContextHolder.getContext()
				.map(it -> it.getUserId()
						.getValue());
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(ExtendedPostgresTemplates.DEFAULT, entityManager);
	}

}
