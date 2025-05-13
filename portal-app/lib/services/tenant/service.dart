import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/services/tenant/models.dart';
import 'package:containerbase/services/tenant/requests.dart';
import 'package:get/get.dart';

import '../clients/rest/apis/tenant/tenant_api.dart';
import 'state.dart';

class TenantService extends GetxService {
  final TenantApiClient tenantApiClient;
  final state = TenantState();

  TenantService({required this.tenantApiClient});

  TenantModel getCurrentTenant() {
    return state.currentTenant.value;
  }

  Future<PagingResponse<TenantModel>> queryTenants(PageRequest<TenantFilter?> pageRequest) {
    return tenantApiClient
        .queryTenants(
          pageRequest.page,
          pageRequest.size,
          filterByTitle: pageRequest.filter?.byName,
          sortFields: pageRequest.sortFields,
          sortDirection: pageRequest.sortDirection,
        )
        .then((response) {
          return PagingResponse<TenantModel>(
            content: response.content.map((tenant) => TenantModel.fromTenantResponse(tenant)).toList(),
            totalElements: response.totalElements,
            totalPages: response.totalPages,
            page: response.page,
          );
        });
  }
}
