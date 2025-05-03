package io.vinta.containerbase.security.scanner;

import io.vinta.containerbase.common.security.permissions.ContainerBaseApiAuthorized;
import io.vinta.containerbase.security.apiregistry.entities.ApiInfo;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface ApiInfoScanner {
	Set<ApiInfo> scan(List<String> basePackages);

	default List<ApiInfo> buildApiInfo(Method method) {
		final List<ApiInfo> apiInfoList = Arrays.stream(method.getDeclaredAnnotations())
				.map(annotation -> {
					if (annotation instanceof GetMapping get) {
						final var paths = handleRequestMappingPaths(get.path(), get.name(), get.value());

						return paths.stream()
								.map(path -> ApiInfo.builder()
										.path(path)
										.serviceId(getServiceId(path))
										.method(HttpMethod.GET)
										.build())
								.toList();
					}
					if (annotation instanceof PutMapping put) {
						final var paths = handleRequestMappingPaths(put.path(), put.name(), put.value());

						return paths.stream()
								.map(path -> ApiInfo.builder()
										.path(path)
										.serviceId(getServiceId(path))
										.method(HttpMethod.PUT)
										.build())
								.toList();
					}
					if (annotation instanceof PostMapping post) {
						final var paths = handleRequestMappingPaths(post.path(), post.name(), post.value());
						return paths.stream()
								.map(path -> ApiInfo.builder()
										.path(path)
										.serviceId(getServiceId(path))
										.method(HttpMethod.POST)
										.build())
								.toList();
					}
					if (annotation instanceof DeleteMapping delete) {
						final var paths = handleRequestMappingPaths(delete.path(), delete.name(), delete.value());

						return paths.stream()
								.map(path -> ApiInfo.builder()
										.path(path)
										.serviceId(getServiceId(path))
										.method(HttpMethod.DELETE)
										.build())
								.toList();
					}
					if (annotation instanceof PatchMapping patch) {
						final var paths = handleRequestMappingPaths(patch.path(), patch.name(), patch.value());

						return paths.stream()
								.map(path -> ApiInfo.builder()
										.path(path)
										.serviceId(getServiceId(path))
										.method(HttpMethod.PATCH)
										.build())
								.toList();
					}
					if (annotation instanceof RequestMapping requestMapping) {
						final var paths = handleRequestMappingPaths(requestMapping.path(), requestMapping.name(),
								requestMapping.value());

						return paths.stream()
								.map(path -> Arrays.stream(requestMapping.method())
										.map(httpMethod -> ApiInfo.builder()
												.path(path)
												.serviceId(getServiceId(path))
												.method(httpMethod.asHttpMethod())
												.build())
										.toList())
								.flatMap(List::stream)
								.toList();
					}
					return List.<ApiInfo>of();
				})
				.flatMap(List::stream)
				.toList();

		final var apiAuthorizedAnnotation = Arrays.stream(method.getDeclaredAnnotations())
				.filter(anno -> anno instanceof ContainerBaseApiAuthorized)
				.map(anno -> (ContainerBaseApiAuthorized) anno)
				.findFirst()
				.orElse(null);

		if (apiAuthorizedAnnotation == null) {
			return List.of();
		}
		return apiInfoList.stream()
				.map(apiInfo -> apiInfo.withSecurityLevel(apiAuthorizedAnnotation.security())
						.withPermissionKeys(Arrays.stream(apiAuthorizedAnnotation.permissions())
								.collect(Collectors.toSet())))
				.toList();
	}

	default List<String> handleRequestMappingPaths(String[] path, String name, String[] value) {
		final var streamPaths = Stream.concat(Arrays.stream(path), Arrays.stream(value));
		return Stream.concat(streamPaths, Optional.of(name)
				.stream())
				.filter(StringUtils::hasText)
				.toList();
	}

	default String getServiceId(String path) {
		return getPathUpToSecondSlash(path);

	}

	default String getPathUpToSecondSlash(String path) {
		int firstSlash = path.indexOf('/', 1); // Skip the leading slash
		if (firstSlash == -1)
			return path;

		int secondSlash = path.indexOf('/', firstSlash + 1);
		if (secondSlash == -1)
			return path;

		return path.substring(0, secondSlash);
	}
}
