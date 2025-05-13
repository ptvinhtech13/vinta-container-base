import 'package:containerbase/services/roles/service.dart';
import 'package:get/get.dart';

class RoleBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<RoleService>(() => RoleService());
  }
}
