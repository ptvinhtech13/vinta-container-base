import 'dart:async';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:get/get.dart';
import 'package:containerbase/services/navigation_config/index.dart';
import 'package:vinta_shared_commons/index.dart';

import 'commons/index.dart';
import 'commons/routes/index.dart';
import 'services/user_authentication/service.dart';

Future<void> main() async {
  return runZonedGuarded(
    () async {
      WidgetsFlutterBinding.ensureInitialized();
      await AppBindings().dependencies();
      await Get.find<UserAuthenticationService>().checkUserAuthentication();
      await Get.find<NavigationItemConfigService>().loadNavigationItemConfig();

      runApp(
        GetMaterialApp(
          useInheritedMediaQuery: true,
          debugShowCheckedModeBanner: false,
          themeMode: ThemeMode.light,
          initialRoute: AppRoutes.welcome,
          getPages: AppRoutingConfigurations.routes,
          locale: const Locale("en_US"),
          fallbackLocale: const Locale("en_US"),
          translations: AppTranslations(),
          navigatorObservers: [FlutterSmartDialog.observer],
          builder: FlutterSmartDialog.init(),
        ),
      );
    },
    (error, stack) {
      log("MAIN LOGGING");
      log("ERROR IN MAIN", error: error, stackTrace: stack);
      Get.find<AppNotificationCenterManager>().sendNotificationMessage(
        AppNotificationMessage(
          header: AppNotificationHeader(),
          content: AppNotifyErrorContent(errorCode: CommonErrorCodeEnum.appBadState.buildErrorCode(), exception: error),
        ),
      );
    },
  );
}
