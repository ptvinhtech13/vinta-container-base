import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/roles/requests.dart';
import 'package:get/get.dart';

import '../../commons/widgets/add_role_permission_modal/view.dart';
import '../../services/roles/service.dart';
import '../../services/users/models.dart';
import 'state.dart';

class RolePermissionPageController extends GetxController {
  final state = RolePermissionPageState();
  final _roleService = Get.find<RoleService>();

  Future<PagingResponse<UserRoleModel>> queryUserRoles(PageRequest<UserRoleFilter?> pageRequest) {
    return _roleService.queryUserRoles(pageRequest);
  }

  Future<dynamic> showAddRoleModal() {
    return Get.dialog<dynamic>(
      AddRolePermissionModal(),
      barrierDismissible: false,
    );
  }

}
