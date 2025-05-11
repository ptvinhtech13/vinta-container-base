import 'package:get/get.dart';

import 'models.dart';

class VintaPagingDataTableState<Model, Filter> {
  final pageRequest = PageRequest<Filter?>(page: 0, size: 20, filter: null, totalElements: 0, totalPages: 0).obs;

  final paginatedDataItem = <Model>[].obs;

  final columnSettings = <DataColumnSetting>[].obs;
}
