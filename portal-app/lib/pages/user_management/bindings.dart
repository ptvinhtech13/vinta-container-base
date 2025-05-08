import 'package:get/get.dart';

import 'controller.dart';

class UserManagementPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<UserManagementPageController>(() => UserManagementPageController());
  }
}
