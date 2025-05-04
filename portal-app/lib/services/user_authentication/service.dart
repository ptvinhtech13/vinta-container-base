import 'package:get/get.dart';
import 'package:vinta_shared_commons/repository/index.dart';

import '../../commons/constants/storage.dart';
import 'state.dart';

class UserAuthenticationService extends GetxService {
  final state = UserAuthenticationState();
  final simpleRepository = Get.find<SimpleRepository>();

  @override
  void onReady() {
    super.onReady();
  }

  Future<void> login(String username, String password) {
    return Future.delayed(const Duration(seconds: 1), () {
      state.isAuthenticated.value = true;
      simpleRepository.saveString(SharePreferenceKeys.userAuthEmailKey, username);
      simpleRepository.saveString(SharePreferenceKeys.userAuthPasswordKey, password);
    });
  }

  Future<void> checkUserAuthentication() {
    final email = simpleRepository.getString(SharePreferenceKeys.userAuthEmailKey);
    final password = simpleRepository.getString(SharePreferenceKeys.userAuthPasswordKey);
    return Future.delayed(Duration(seconds: 2), () => state.isAuthenticated.value = email.isNotEmpty && password.isNotEmpty);
  }
}
