import 'package:get/get.dart';
import 'package:intl/intl.dart';

import 'state.dart';

class TenantManagementPageController extends GetxController {
  final state = TenantManagementPageState();

  @override
  void onInit() {
    super.onInit();
    _loadMockData();
    filterTenants();
  }

  void toggleFilterPanel() {
    state.isFilterPanelExpanded.value = !state.isFilterPanelExpanded.value;
  }

  void clearFilters() {
    state.searchText.value = '';
    state.selectedStatus.value = null;
    filterTenants();
  }

  void filterTenants() {
    final searchText = state.searchText.value.toLowerCase();
    final status = state.selectedStatus.value;

    state.filteredTenants.value = state.tenants.where((tenant) {
      bool matchesSearch = searchText.isEmpty ||
          tenant.tenantName.toLowerCase().contains(searchText);

      bool matchesStatus = status == null || tenant.status == status;

      return matchesSearch && matchesStatus;
    }).toList();
  }

  void toggleColumnVisibility(String columnName) {
    if (state.visibleColumns.contains(columnName)) {
      if (state.visibleColumns.length > 1) { // Ensure at least one column is visible
        state.visibleColumns.remove(columnName);
      }
    } else {
      state.visibleColumns.add(columnName);
    }
  }

  bool isColumnVisible(String columnName) {
    return state.visibleColumns.contains(columnName);
  }

  String formatDate(DateTime date) {
    return DateFormat('yyyy-MM-dd HH:mm').format(date);
  }

  void _loadMockData() {
    final mockTenants = [
      TenantModel(
        tenantId: 'TN001',
        tenantName: 'Acme Corporation',
        createdAt: DateTime(2023, 5, 15, 9, 30),
        creator: 'John Admin',
        domainUrl: 'acme.example.com',
        status: 'ACTIVE',
      ),
      TenantModel(
        tenantId: 'TN002',
        tenantName: 'Globex Industries',
        createdAt: DateTime(2023, 6, 22, 14, 45),
        creator: 'Sarah Manager',
        domainUrl: 'globex.example.com',
        status: 'ACTIVE',
      ),
      TenantModel(
        tenantId: 'TN003',
        tenantName: 'Oceanic Airlines',
        createdAt: DateTime(2023, 7, 10, 11, 15),
        creator: 'Mike Supervisor',
        domainUrl: 'oceanic.example.com',
        status: 'IN-ACTIVE',
      ),
      TenantModel(
        tenantId: 'TN004',
        tenantName: 'Stark Industries',
        createdAt: DateTime(2023, 8, 5, 16, 20),
        creator: 'Tony Admin',
        domainUrl: 'stark.example.com',
        status: 'CREATED',
      ),
      TenantModel(
        tenantId: 'TN005',
        tenantName: 'Wayne Enterprises',
        createdAt: DateTime(2023, 9, 12, 10, 0),
        creator: 'Bruce Manager',
        domainUrl: 'wayne.example.com',
        status: 'ACTIVE',
      ),
    ];

    state.tenants.addAll(mockTenants);
  }
}
