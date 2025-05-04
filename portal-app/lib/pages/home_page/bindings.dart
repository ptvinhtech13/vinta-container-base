import 'package:get/get.dart';
import 'package:containerbase/pages/home_page/controller.dart';

class HomePageBinding extends Bindings {
  @override
  void dependencies() {
    Get.lazyPut<HomePageController>(() => HomePageController());
  }
}
