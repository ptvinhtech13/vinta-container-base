import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../services/user_authentication/service.dart';
import '../routes/app_routes.dart';

class UserAccessCheckerMiddleware extends GetMiddleware {
  @override
  int? priority = 0;

  UserAccessCheckerMiddleware({required this.priority});

  @override
  RouteSettings? redirect(String? route) {
    final userAuthService = Get.find<UserAuthenticationService>();
    userAuthService.evaluateUserAuthentication();

    log("redirect route $route ${userAuthService.state.isAuthenticated.value}");

    if (userAuthService.state.isAuthenticated.value) {
      if (route == AppRoutes.welcome) {
        Future.delayed(const Duration(milliseconds: 20), () async => {Get.offNamed(AppRoutes.home)});
      }
      return null;
    } else {
      Future.delayed(const Duration(milliseconds: 20), () async => {Get.offNamed(AppRoutes.welcome)});
      return null;
    }
  }
}
