import 'package:get/get.dart';

import 'controller.dart';

class AccountPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<AccountPageController>(() => AccountPageController());
  }
}
