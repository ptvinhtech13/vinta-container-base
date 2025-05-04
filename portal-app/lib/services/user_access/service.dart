import 'package:get/get.dart';
import 'package:vinta_shared_commons/repository/index.dart';

import '../../commons/constants/storage.dart';
import '../clients/rest/apis/user_access/dtos.dart';
import '../clients/rest/apis/user_access/user_access_api.dart';

class UserAccessService extends GetxService {
  final SimpleRepository _simpleRepository;

  final UserAccessApiClient _userAccessApiClient;

  UserAccessService({required SimpleRepository simpleRepository, @override required UserAccessApiClient userAccessApiClient})
    : _simpleRepository = simpleRepository,
      _userAccessApiClient = userAccessApiClient;

  void onReady() {
    super.onReady();
  }

  Future<void> login(String email, String password) {
    return _userAccessApiClient.login(LoginRequest(accessType: "BASIC_AUTH", email: email, password: password)).then((response) {
      _simpleRepository.saveString(SharePreferenceKeys.userAuthTokenKey, response.accessToken.token);
      _simpleRepository.saveString(SharePreferenceKeys.userAuthTokenExpiredAtKey, response.accessToken.expiresAt.toIso8601String());
      _simpleRepository.saveString(SharePreferenceKeys.userAuthRefreshTokenKey, response.refreshToken.token);
      _simpleRepository.saveString(SharePreferenceKeys.userAuthRefreshTokenExpiredAtKey, response.refreshToken.expiresAt.toIso8601String());
      return Future.value();
    });
  }
}
