import 'package:get/get.dart';

import 'controller.dart';

class RolePermissionPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<RolePermissionPageController>(() => RolePermissionPageController());
  }
}
