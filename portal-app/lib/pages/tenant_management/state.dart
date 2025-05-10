import 'package:get/get.dart';

class TenantManagementPageState {
  // Filter states
  final searchText = ''.obs;
  final isFilterPanelExpanded = true.obs;
  final selectedStatus = Rx<String?>(null);

  // Table states
  final tenants = <TenantModel>[].obs;
  final filteredTenants = <TenantModel>[].obs;
  final paginatedTenants = <TenantModel>[].obs;
  final visibleColumns = <String>['tenantId', 'tenantName', 'createdAt'].obs;

  // Pagination states
  final currentPage = 1.obs;
  final pageSize = 10.obs;
  final pageSizeOptions = [10, 100, 500];
  final totalPages = 1.obs;

  // Sorting states
  final sortColumnIndex = Rx<int?>(null);
  final sortAscending = true.obs;

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
  final String description;
  final String contactEmail;
  final String contactPhone;
  final String address;
  final int usersCount;
  final String industry;
  final String subscriptionType;
  final DateTime lastLoginAt;

  TenantModel({
    required this.tenantId,
    required this.tenantName,
    required this.createdAt,
    required this.creator,
    required this.domainUrl,
    required this.status,
    required this.description,
    required this.contactEmail,
    required this.contactPhone,
    required this.address,
    required this.usersCount,
    required this.industry,
    required this.subscriptionType,
    required this.lastLoginAt,
  });
}
