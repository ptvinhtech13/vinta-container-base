import 'package:containerbase/services/tenant/index.dart';
import 'package:get/get.dart';

import '../../services/user_authentication/service.dart';
import 'state.dart';

class AppPageController extends GetxController {
  final userAuthenticationService = Get.find<UserAuthenticationService>();
  final tenantService = Get.find<TenantService>();

  final state = AppPageState();

  void showLoading() {
    state.isShowLoadingCounter.value++;
  }

  void tryCloseLoading() {
    state.isShowLoadingCounter.value--;
  }
}
