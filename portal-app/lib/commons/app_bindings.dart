import 'dart:async';
import 'dart:developer';
import 'dart:io';

import 'package:containerbase/config/env_config.dart';
import 'package:containerbase/services/clients/rest/apis/tenant/tenant_api.dart';
import 'package:containerbase/services/roles/bindings.dart';
import 'package:containerbase/services/users/bindings.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/global_bindings.dart';
import 'package:vinta_shared_commons/repository/simple_repository.dart';

import '../services/clients/rest/apis/roles/api.dart';
import '../services/clients/rest/apis/user_access/user_access_api.dart';
import '../services/clients/rest/apis/users/api.dart';
import '../services/clients/rest/config/dio_service.dart';
import '../services/navigation/bindings.dart';
import '../services/tenant/bindings.dart';
import '../services/user_access/service.dart';
import '../services/user_authentication/index.dart';

class AppBindings extends Bindings {
  @override
  Future<void> dependencies() async {
    await VintaSharedComponentBindings().dependencies(
      isServerConnectionErrorRetryer: (Exception e) {
        return e is SocketException || e is TimeoutException;
      },
    );
    final containerBaseDioService = Get.put<ContainerBaseDioService>(ContainerBaseDioService(Get.find<SimpleRepository>()), permanent: true);
    final userAccessApiClient = Get.put<UserAccessApiClient>(
      UserAccessApiClient(containerBaseDioService.containerBaseDioTokenAccessServer, baseUrl: EnvConfig.apiHost),
      permanent: true,
    );

    Get.put<TenantApiClient>(TenantApiClient(containerBaseDioService.containerBaseDioServer, baseUrl: EnvConfig.apiHost));
    Get.put<RoleApiClient>(RoleApiClient(containerBaseDioService.containerBaseDioServer, baseUrl: EnvConfig.apiHost));
    Get.put<UserApiClient>(UserApiClient(containerBaseDioService.containerBaseDioServer, baseUrl: EnvConfig.apiHost));

    Get.put<UserAccessService>(UserAccessService(simpleRepository: Get.find<SimpleRepository>(), userAccessApiClient: userAccessApiClient));

    TenantServiceBindings().dependencies();
    UsersBindings().dependencies();
    RoleBindings().dependencies();
    UserAuthenticationBindings().dependencies();
    NavigationItemConfigBindings().dependencies();

    log("Services initialized");
  }
}
