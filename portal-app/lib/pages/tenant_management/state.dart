import 'package:get/get.dart';

class TenantManagementPageState {
  // Filter states
  final searchText = ''.obs;
  final isFilterPanelExpanded = true.obs;
  final selectedStatus = Rx<String?>(null);

  // Table states
  final tenants = <TenantModel>[].obs;
  final filteredTenants = <TenantModel>[].obs;
  final visibleColumns = <String>['tenantId', 'tenantName', 'createdAt'].obs;

  // Available statuses for filter
  final availableStatuses = ['ACTIVE', 'CREATED', 'IN-ACTIVE'];
}

class TenantModel {
  final String tenantId;
  final String tenantName;
  final DateTime createdAt;
  final String creator;
  final String domainUrl;
  final String status;

  TenantModel({
    required this.tenantId,
    required this.tenantName,
    required this.createdAt,
    required this.creator,
    required this.domainUrl,
    required this.status,
  });
}
