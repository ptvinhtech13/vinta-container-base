import 'dart:async';

import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/index.dart';

import '../../services/user_authentication/index.dart';

class WelcomeController extends GetxController {
  final isVisibleLogin = false.obs;
  final isOtpAlreadySent = false.obs;
  final isOpenLogin = false.obs;
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final userAuthService = Get.find<UserAuthenticationService>();

  @override
  Future<void> onReady() async {
    super.onReady();

    Future.delayed(const Duration(milliseconds: 5000)).then((value) {
      toggleSignIn();
    });
  }

  void hydrate() {}

  Future<void> toggleSignIn() async {
    if (isVisibleLogin.value) {
      isOpenLogin.value = false;
      return AppUtils.delay(const Duration(milliseconds: 1500)).then((value) {
        isVisibleLogin.value = false;
      });
    } else {
      isVisibleLogin.value = true;
      return AppUtils.delay(const Duration(milliseconds: 1500)).then((value) {
        isOpenLogin.value = true;
      });
    }
  }
}
