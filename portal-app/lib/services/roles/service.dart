import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/roles/requests.dart';
import 'package:containerbase/services/users/models.dart';
import 'package:get/get.dart';

import '../clients/rest/apis/roles/api.dart';
import '../tenant/service.dart';

class RoleService extends GetxService {
  final roleApiClient = Get.find<RoleApiClient>();
  final tenantService = Get.find<TenantService>();

  Future<PagingResponse<UserRoleModel>> queryUserRoles(PageRequest<UserRoleFilter?> pageRequest) {
    return roleApiClient.queryRoles(
      pageRequest.page,
      pageRequest.size,
      byRoleIds: pageRequest.filter?.byRoleIds?.toList(),
      byRoleKeys: pageRequest.filter?.byRoleKeys?.toList(),
      sortFields: pageRequest.sortFields,
      sortDirection: pageRequest.sortDirection,
    ).then((response) => PagingResponse<UserRoleModel>(
        page: response.page,
        totalElements: response.totalElements,
        totalPages: response.totalPages,
        content: response.content.map((e) => UserRoleModel.fromRoleResponse(tenantService.state.currentTenant.value.id, e)).toList(),
      ),
    );
  }
}
