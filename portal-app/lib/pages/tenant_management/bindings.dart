import 'package:get/get.dart';

import 'controller.dart';

class TenantManagementPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<TenantManagementPageController>(() => TenantManagementPageController());
  }
}
