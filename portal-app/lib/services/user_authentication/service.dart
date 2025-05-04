import 'package:get/get.dart';
import 'package:vinta_shared_commons/repository/index.dart';

import '../../commons/constants/storage.dart';
import '../user_access/service.dart';
import 'state.dart';

class UserAuthenticationService extends GetxService {
  final state = UserAuthenticationState();
  final UserAccessService _userAccessService;
  final SimpleRepository _simpleRepository;

  UserAuthenticationService({required SimpleRepository simpleRepository, required UserAccessService userAccessService})
    : _simpleRepository = simpleRepository,
      _userAccessService = userAccessService;

  @override
  Future<void> onReady() async {
    super.onReady();
  }

  Future<void> login(String username, String password) {
    return _userAccessService.login(username, password).then((value) {
      state.isAuthenticated.value = true;
    });
  }

  Future<void> checkUserAuthentication() {
    final accessTokenExpiredAt = _simpleRepository.getString(SharePreferenceKeys.userAuthTokenExpiredAtKey);
    if (accessTokenExpiredAt.isNotEmpty) {
      final expiredAt = DateTime.parse(accessTokenExpiredAt).millisecondsSinceEpoch;
      final now = DateTime.now().millisecondsSinceEpoch;
      state.isAuthenticated.value = expiredAt > now;
    } else {
      logout();
      state.isAuthenticated.value = false;
    }
    return Future.value();
  }

  void logout() {
    _simpleRepository.remove(SharePreferenceKeys.userAuthTokenKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthTokenExpiredAtKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthRefreshTokenKey);
    _simpleRepository.remove(SharePreferenceKeys.userAuthRefreshTokenExpiredAtKey);
    state.isAuthenticated.value = false;
  }
}
