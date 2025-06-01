import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/users/requests.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../commons/widgets/add_user_modal/view.dart';
import '../../services/clients/rest/apis/users/dtos.dart';
import '../../services/roles/models.dart';
import '../../services/roles/requests.dart';
import '../../services/roles/service.dart';
import '../../services/tenant/service.dart';
import '../../services/users/models.dart';
import '../../services/users/service.dart';
import 'state.dart';

class AccountPageController extends GetxController {
  final state = AccountPageState();
  final _userService = Get.find<UserService>();
  final _roleService = Get.find<RoleService>();
  final _tenantService = Get.find<TenantService>();

  final availableRoles = <RoleModel>[];
  final isLoadingRoles = false.obs;

  Future<PagingResponse<UserModel>> queryUsers(PageRequest<UserFilter?> pageRequest) {
    return _userService.queryUsers(pageRequest);
  }

  Future<void> _loadAvailableRoles() async {
    try {
      final roles = await _roleService
          .queryUserRoles(PageRequest<UserRoleFilter?>(page: 0, size: 500))
          .then((response) => response.content.map((e) => e.roleModel).toSet().toList());
      availableRoles.clear();
      availableRoles.addAll(roles);
    } catch (e) {
      Get.snackbar('Error', 'Failed to load roles: ${e.toString()}', backgroundColor: Colors.red.shade100, colorText: Colors.red.shade800);
    }
  }

  Future<UserModel?> showAddUserModal() {
    return Get.dialog<UserModel>(
      AddUserModal(availableRoles: availableRoles, tenantId: _tenantService.getCurrentTenant().id, onSave: _handleCreateUser),
      barrierDismissible: false,
    );
  }

  Future<UserModel> _handleCreateUser(CreateUserRequest request) async {
    return _userService.createUser(request);
  }

  void hydrate() {
    _loadAvailableRoles();
  }
}
