import 'package:get/get.dart';

import 'service.dart';

class NavigationItemConfigBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(NavigationItemService());
  }
}
