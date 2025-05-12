import 'package:get/get.dart';

import 'state.dart';

class AppPageController extends GetxController {
  final state = AppPageState();

  void showLoading() {
    state.isShowLoadingCounter.value++;
  }

  void tryCloseLoading() {
    state.isShowLoadingCounter.value--;
  }
}
