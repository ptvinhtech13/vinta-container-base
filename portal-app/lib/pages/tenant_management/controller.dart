import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/tenant/index.dart';
import 'package:containerbase/services/tenant/requests.dart';
import 'package:get/get.dart';

import 'state.dart';

class TenantManagementPageController extends GetxController {
  final tenantService = Get.find<TenantService>();
  final state = TenantManagementPageState();

  Future<PagingResponse<TenantModel>> queryTenants(PageRequest<TenantFilter?> pageRequest) {
    return tenantService.queryTenants(pageRequest);
  }
}
