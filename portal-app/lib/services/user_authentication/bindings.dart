import 'package:get/get.dart';
import 'package:vinta_shared_commons/repository/index.dart';

import '../user_access/service.dart';
import 'service.dart';

class UserAuthenticationBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(UserAuthenticationService(simpleRepository: Get.find<SimpleRepository>(), userAccessService: Get.find<UserAccessService>()));
  }
}
