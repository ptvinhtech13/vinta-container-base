import 'package:get/get.dart';

import 'controller.dart';

class AddRolePermissionModalBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(AddRolePermissionModalController());
  }
}