import 'package:containerbase/services/users/models.dart';
import 'package:get/get.dart';

class UserAuthenticationState {
  final isAuthenticated = false.obs;
  final currentUserId = Rxn<String>(null);
  final currentUser = Rxn<UserModel>(null);
}
