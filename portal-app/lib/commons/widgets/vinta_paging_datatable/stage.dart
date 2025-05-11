import 'package:get/get.dart';

import 'models.dart';

class VintaPagingDataTableState<Model, Filter> {
  final pageRequest =
      PageRequest<Filter?>(page: 0, size: 20, filter: null, totalElements: 0, totalPages: 0, sortFields: null, sortDirection: null).obs;

  final paginatedDataItem = <Model>[].obs;

  final columnSettings = <DataColumnSetting>[].obs;

  // Track the currently sorted column
  final sortedColumnKey = RxnString(null);
  final isAscending = true.obs;

  // Track which column is being hovered for sort icon display
  final hoveredColumnKey = RxnString(null);
}
