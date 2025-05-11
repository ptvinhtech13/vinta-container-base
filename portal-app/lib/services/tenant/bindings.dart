import 'package:containerbase/services/tenant/service.dart';
import 'package:get/get.dart';

import '../clients/rest/apis/tenant/tenant_api.dart';

class TenantServiceBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<TenantService>(() => TenantService(tenantApiClient: Get.find<TenantApiClient>()));
  }
}
