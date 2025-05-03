package io.vinta.containerbase.security.apiregistry;

import io.vinta.containerbase.security.apiregistry.entities.ApiInfo;
import java.util.List;

public interface ApiInfoRegistry {
	List<ApiInfo> getPrivateApis();

	List<ApiInfo> getPublicApis();

	List<ApiInfo> getAuthorizedRoleApis();

	List<ApiInfo> getAuthenticatedApis();
}
