import 'package:containerbase/commons/constants/commons.dart';
import 'package:get/get.dart';

import 'models.dart';

class TenantState {
  final currentTenant = Rx<TenantModel>(DomainConstants.defaultTenant);
}
