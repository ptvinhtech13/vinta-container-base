import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/clients/rest/apis/roles/api.dart';
import 'package:containerbase/services/users/requests.dart';
import 'package:get/get.dart';

import '../clients/rest/apis/users/api.dart';
import '../clients/rest/apis/users/dtos.dart';
import '../roles/models.dart';
import '../tenant/service.dart';
import 'models.dart';

class UserService extends GetxService {
  final _userApiClient = Get.find<UserApiClient>();
  final _roleApiClient = Get.find<RoleApiClient>();
  final _tenantService = Get.find<TenantService>();

  Future<PagingResponse<UserModel>> queryUsers(PageRequest<UserFilter?> pageRequest) {
    return _userApiClient
        .queryUsers(
          pageRequest.page,
          pageRequest.size,
          sortFields: pageRequest.sortFields,
          sortDirection: pageRequest.sortDirection,
          byUserId: pageRequest.filter?.byUserId,
          byTenantId: pageRequest.filter?.byTenantId,
          byRoleIds: pageRequest.filter?.byRoleIds?.toList(),
          byUserTypes: pageRequest.filter?.byUserTypes?.toList(),
          byUserStatuses: pageRequest.filter?.byUserStatuses?.toList(),
          byUserIds: pageRequest.filter?.byUserIds?.toList(),
          byPhoneNumber: pageRequest.filter?.byPhoneNumber,
          byEmail: pageRequest.filter?.byEmail,
          byContainingEmail: pageRequest.filter?.byContainingEmail,
          byName: pageRequest.filter?.byName,
          byCreatedRangeFrom: pageRequest.filter?.byCreatedRange?.from,
          byCreatedRangeTo: pageRequest.filter?.byCreatedRange?.to,
        )
        .then((response) {
          final byRoleIds = response.content.map((e) => e.userRoles.map((e) => e.roleId).toSet()).expand((roleId) => roleId).toSet().toList();

          return _roleApiClient.queryRoles(0, 500, byRoleIds: byRoleIds).then((roleResponse) {
            final roleMap = <String, RoleModel>{};
            for (final role in roleResponse.content) {
              roleMap[role.id] = RoleModel.fromResponse(role);
            }
            return PagingResponse(
              content: response.content.map((e) => UserModel.fromResponse(e, roleMap)).toList(),
              page: response.page,
              totalElements: response.totalElements,
              totalPages: response.totalPages,
            );
          });
        });
  }

  Future<UserModel> getUserMe() {
    return _userApiClient.getUserMe().then((response) {
      final byRoleIds = response.userRoles.map((e) => e.roleId).toSet().toList();
      return _roleApiClient.queryRoles(0, 500, byRoleIds: byRoleIds).then((roleResponse) {
        final roleMap = <String, RoleModel>{};
        for (final role in roleResponse.content) {
          roleMap[role.id] = RoleModel.fromResponse(role);
        }
        return UserModel.fromResponse(response, roleMap);
      });
    });
  }

  Future<UserModel> createUser(CreateUserRequest request) {
    return _userApiClient.createUser(request).then((response) {
      final byRoleIds = response.userRoles.map((e) => e.roleId).toSet().toList();
      return _roleApiClient.queryRoles(0, 500, byRoleIds: byRoleIds).then((roleResponse) {
        final roleMap = <String, RoleModel>{};
        for (final role in roleResponse.content) {
          roleMap[role.id] = RoleModel.fromResponse(role);
        }
        return UserModel.fromResponse(response, roleMap);
      });
    });
  }
}
