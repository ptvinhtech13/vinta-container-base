import 'package:get/get.dart';

import 'controller.dart';

class DemoMetabasePageBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<DemoMetabaseController>(() => DemoMetabaseController());
  }
}
