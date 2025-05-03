package io.vinta.containerbase.security.apiregistry;

import io.vinta.containerbase.common.security.permissions.PlatformApiSecurityLevel;
import io.vinta.containerbase.security.apiregistry.entities.ApiInfo;
import io.vinta.containerbase.security.scanner.ApiInfoScanner;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiInfoRegistryImpl implements ApiInfoRegistry {
	private final ApiInfoScanner scanner;

	@Getter
	private final List<ApiInfo> privateApis = new ArrayList<>();

	@Getter
	private final List<ApiInfo> publicApis = new ArrayList<>();

	@Getter
	private final List<ApiInfo> authenticatedApis = new ArrayList<>();

	@Getter
	private final List<ApiInfo> authorizedRoleApis = new ArrayList<>();

	@Value("${io.vinta.containerbase.scanning-apis.base-packages}")
	private String apiScannedBasePackages;

	@PostConstruct
	public void init() {
		log.info("ApiScannedBasePackages: {}", apiScannedBasePackages);
		final var apiInfoList = scanner.scan(Arrays.stream(apiScannedBasePackages.split(";"))
				.filter(StringUtils::hasText)
				.map(String::strip)
				.map(String::trim)
				.toList());

		privateApis.addAll(apiInfoList.stream()
				.filter(apiInfo -> PlatformApiSecurityLevel.PRIVATE.equals(apiInfo.getSecurityLevel()))
				.toList());

		publicApis.addAll(apiInfoList.stream()
				.filter(apiInfo -> PlatformApiSecurityLevel.PUBLIC.equals(apiInfo.getSecurityLevel()))
				.toList());

		authenticatedApis.addAll(apiInfoList.stream()
				.filter(apiInfo -> PlatformApiSecurityLevel.AUTHENTICATED.equals(apiInfo.getSecurityLevel()))
				.toList());

		authorizedRoleApis.addAll(apiInfoList.stream()
				.filter(api -> PlatformApiSecurityLevel.AUTHENTICATED_PERMISSIONS.equals(api.getSecurityLevel())
						&& !CollectionUtils.isEmpty(api.getPermissionKeys()))
				.toList());

		if (log.isDebugEnabled()) {
			debugApiInfoList(privateApis, "Private APIs");
			debugApiInfoList(publicApis, "Public APIs");
			debugApiInfoList(authenticatedApis, "Authenticated APIs");
			debugApiInfoList(authorizedRoleApis, "Authorized Permissions APIs");
		}

	}

	private void debugApiInfoList(List<ApiInfo> apis, String name) {
		apis.stream()
				.collect(Collectors.groupingBy(ApiInfo::getServiceId))
				.forEach((key, value) -> log.debug("{} [{}]: Size: [{}], API List: {}", name, key, value.size(), value
						.stream()
						.sorted(Comparator.comparing(ApiInfo::getPath))
						.map(it -> String.format("[%s: %s, %s]", it.getMethod(), it.getPath(), it.getPermissionKeys()))
						.toList()));

	}
}
