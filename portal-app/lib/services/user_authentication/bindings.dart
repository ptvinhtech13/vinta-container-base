import 'package:get/get.dart';

import 'service.dart';

class UserAuthenticationBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(UserAuthenticationService());
  }
}