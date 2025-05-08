import 'package:get/get.dart';

import 'controller.dart';

class NotFoundPageBindings extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<NotFoundPageController>(() => NotFoundPageController());
  }
}
