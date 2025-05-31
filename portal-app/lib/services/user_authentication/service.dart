import 'dart:async';
import 'dart:developer';

import 'package:dio/dio.dart';
import 'package:get/get.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:vinta_shared_commons/notifications/app_notification_service.dart';
import 'package:vinta_shared_commons/notifications/content_models.dart';
import 'package:vinta_shared_commons/notifications/models.dart';
import 'package:vinta_shared_commons/repository/index.dart';

import '../../commons/constants/storage.dart';
import '../../commons/exceptions/error_response.dart';
import '../../commons/routes/app_routes.dart';
import '../user_access/service.dart';
import '../users/service.dart';
import 'state.dart';

class UserAuthenticationService extends GetxService {
  final state = UserAuthenticationState();

  final UserAccessService _userAccessService;
  final SimpleRepository _simpleRepository;
  final UserService _userService;

  final _tokenAuditIntervalDuration = Duration(minutes: 3);
  final _tokenRefreshDifferenceDuration = Duration(minutes: 6); // > =2x of tokenAuditIntervalDuration

  UserAuthenticationService({
    required SimpleRepository simpleRepository,
    required UserAccessService userAccessService,
    required UserService userService,
  }) : _simpleRepository = simpleRepository,
       _userAccessService = userAccessService,
       _userService = userService;

  @override
  Future<void> onReady() async {
    super.onReady();
    await evaluateUserAuthentication();
    Timer.periodic(_tokenAuditIntervalDuration, (timer) {
      evaluateUserAuthentication();
    });
  }

  Future<void> login(String username, String password) {
    return _userAccessService.login(username, password).then((value) async {
      state.isAuthenticated.value = true;
      await settleCurrentUser();
    });
  }

  Future<void> evaluateUserAuthentication() async {
    final hasAuthenticated =
        _simpleRepository.getString(SharePreferenceKeys.userAuthTokenKey).isNotEmpty &&
        _simpleRepository.getString(SharePreferenceKeys.userAuthTokenExpiredAtKey).isNotEmpty &&
        _simpleRepository.getString(SharePreferenceKeys.userAuthRefreshTokenKey).isNotEmpty &&
        _simpleRepository.getString(SharePreferenceKeys.userAuthRefreshTokenExpiredAtKey).isNotEmpty;
    state.isAuthenticated.value = true;
    if (!hasAuthenticated) {
      logout();
      return Future.value();
    }

    final refreshTokenExpiredAt = DateTime.parse(_simpleRepository.getString(SharePreferenceKeys.userAuthRefreshTokenExpiredAtKey));
    final differenceRefreshTokenExpired = refreshTokenExpiredAt.difference(DateTime.now());
    if (refreshTokenExpiredAt.isBefore(DateTime.now()) || differenceRefreshTokenExpired.inMinutes < 1) {
      logout();
      return Future.value();
    }

    await settleCurrentUser();

    if (differenceRefreshTokenExpired.inMinutes < _tokenRefreshDifferenceDuration.inMinutes) {
      await refreshToken().catchError((error) {
        log("Error refreshing token");
        logout();

        if (error is DioException && error.error is ErrorResponse) {
          final errorResponse = error.error as ErrorResponse;
          Get.find<AppNotificationCenterManager>().sendNotificationMessage(
            AppNotificationMessage(
              header: AppNotificationHeader(),
              content: AppNotifyErrorContent(errorCode: errorResponse.errorCode, errorMessage: "${errorResponse.message} ${errorResponse.path}"),
            ),
          );
          return;
        }
      });
    }
    return Future.value();
  }

  Future<void> settleCurrentUser() async {
    final tokenClaim = JwtDecoder.decode(_simpleRepository.getString(SharePreferenceKeys.userAuthTokenKey));
    log("tokenClaim['tokenClaim']['userId': ${tokenClaim['tokenClaim']['userId']}");
    state.currentUserId.value = tokenClaim['tokenClaim']['userId'];
    state.currentUser.value = await _userService.getUserMe();
  }

  void logout() {
    _simpleRepository.remove(SharePreferenceKeys.userAuthTokenKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthTokenExpiredAtKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthRefreshTokenKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthRefreshTokenExpiredAtKey);
    state.currentUserId.value = null;
    state.isAuthenticated.value = false;
    if (Get.key.currentContext != null && Get.currentRoute != AppRoutes.welcome) {
      Future.delayed(Duration(milliseconds: 100), () {
        if (Get.key.currentContext != null && Get.currentRoute != AppRoutes.welcome) {
          Get.offAndToNamed(AppRoutes.welcome);
        }
      });
    }
  }

  Future<void> refreshToken() {
    return _userAccessService.refreshToken().then((value) {
      state.isAuthenticated.value = true;
    });
  }
}
