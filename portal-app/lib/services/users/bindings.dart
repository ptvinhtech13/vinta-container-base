import 'package:containerbase/services/users/service.dart';
import 'package:get/get.dart';

class UsersBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<UserService>(() => UserService());
  }
}
