import 'package:containerbase/pages/tenant_management/state.dart';
import 'package:get/get.dart';

import 'models.dart';

class VintaPagingDataTableState<T> {
  PagingTenantFilter? dataFilter;

  final pageRequest = PageRequest(page: 0, size: 10, totalElements: 0, totalPages: 0).obs;

  final paginatedDataItem = <T>[].obs;

  final columnSettings = <DataColumnSetting>[].obs;
}
