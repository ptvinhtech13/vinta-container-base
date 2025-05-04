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

    if (userAuthService.state.isAuthenticated.value) {
      Future.delayed(const Duration(milliseconds: 700), () async => {Get.offNamed(AppRoutes.home)});
      return null;
    }

    Future.delayed(const Duration(milliseconds: 700), () async => {Get.offNamed(AppRoutes.welcome)});
    return null;
  }
}
