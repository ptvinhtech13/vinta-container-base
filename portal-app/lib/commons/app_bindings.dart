import 'dart:async';
import 'dart:developer';
import 'dart:io';

import 'package:get/get.dart';
import 'package:vinta_shared_commons/index.dart';

import '../services/navigation_config/bindings.dart';
import '../services/user_authentication/index.dart';

class AppBindings extends Bindings {
  @override
  Future<void> dependencies() async {
    await VintaSharedComponentBindings().dependencies(
      isServerConnectionErrorRetryer: (Exception e) {
        return e is SocketException || e is TimeoutException;
      },
    );

    UserAuthenticationBindings().dependencies();
    NavigationItemConfigBindings().dependencies();

    log("Services initialized");
  }
}
