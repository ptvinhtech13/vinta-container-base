/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.security.scanner;

import io.vinta.containerbase.security.apiregistry.entities.ApiInfo;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class SpringBaseApiInfoScannerImpl implements ApiInfoScanner {

	private final Environment environment;

	@Override
	public Set<ApiInfo> scan(List<String> basePackages) {
		return basePackages.stream()
				.map(pack -> scanApiDefinition(pack).stream()
						.toList())
				.flatMap(List::stream)
				.map(bean -> (ScannedGenericBeanDefinition) bean)
				.map(bean -> {
					try {
						return Arrays.stream(ReflectionUtils.getAllDeclaredMethods(Objects.requireNonNull(bean
								.resolveBeanClass(this.getClass()
										.getClassLoader()))))
								.map(this::buildApiInfo)
								.flatMap(List::stream)
								.filter(it -> Objects.nonNull(it) && StringUtils.isNotBlank(it.getPath()))
								.toList();
					} catch (ClassNotFoundException e) {
						log.warn("ClassNotFound: {}", e.getMessage(), e);
						return List.<ApiInfo>of();
					}
				})
				.flatMap(List::stream)
				.collect(Collectors.toSet());
	}

	private Set<BeanDefinition> scanApiDefinition(String basePackage) {
		final var packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils
				.convertClassNameToResourcePath(getEnvironment().resolveRequiredPlaceholders(basePackage))
				+ "/**/*Api.class";
		final Resource[] resources;
		try {
			resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e.getMessage(), e);
		}

		final Predicate<String> skippedResourcePredicate = fileName -> StringUtils.isNotBlank(fileName) && !fileName
				.contains(ClassUtils.CGLIB_CLASS_SEPARATOR);

		final Set<BeanDefinition> apiBeanDefinitions = Arrays.stream(resources)
				.filter(resource -> skippedResourcePredicate.test(resource.getFilename()))
				.map(resource -> {
					final MetadataReader metadataReader;
					try {
						metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
						final var sbd = new ScannedGenericBeanDefinition(metadataReader);
						sbd.setSource(resource);
						return sbd;
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						throw new IllegalStateException(e.getMessage(), e);
					}
				})
				.collect(Collectors.toSet());

		if (log.isDebugEnabled()) {
			log.debug("Scanned APIs Result: [{}], Beans: [{}]", packageSearchPath, apiBeanDefinitions.stream()
					.map(BeanDefinition::getBeanClassName)
					.toList());
		}

		return apiBeanDefinitions;

	}

}
