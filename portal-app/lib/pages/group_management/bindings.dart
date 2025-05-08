import 'package:get/get.dart';

class GroupManagementPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<GroupManagementPageBindings>(() => GroupManagementPageBindings());
  }
}
