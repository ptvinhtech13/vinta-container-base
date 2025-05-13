import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/users/requests.dart';
import 'package:get/get.dart';

import '../../services/users/models.dart';
import '../../services/users/service.dart';
import 'state.dart';

class AccountPageController extends GetxController {
  final state = AccountPageState();
  final _userService = Get.find<UserService>();

  Future<PagingResponse<UserModel>> queryUsers(PageRequest<UserFilter?> pageRequest) {
    return _userService.queryUsers(pageRequest);
  }
}
