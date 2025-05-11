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

    state.filteredTenants.value =
        state.tenants.where((tenant) {
          bool matchesSearch = searchText.isEmpty || tenant.tenantName.toLowerCase().contains(searchText);

          bool matchesStatus = status == null || tenant.status == status;

          return matchesSearch && matchesStatus;
        }).toList();

    // Apply sorting if a sort column is selected
    if (state.sortColumnIndex.value != null) {
      _sortTenants();
    }

    // Update pagination after filtering
    updatePagination();
  }

  void updatePagination() {
    // Calculate total pages
    final totalItems = state.filteredTenants.length;
    final pageSize = state.pageSize.value;

    state.totalPages.value = (totalItems / pageSize).ceil();

    // Ensure current page is valid
    if (state.currentPage.value > state.totalPages.value && state.totalPages.value > 0) {
      state.currentPage.value = state.totalPages.value;
    }

    // Get paginated data
    updatePaginatedData();
  }

  void updatePaginatedData() {
    final startIndex = (state.currentPage.value - 1) * state.pageSize.value;
    final endIndex = startIndex + state.pageSize.value;

    if (state.filteredTenants.isEmpty) {
      state.paginatedTenants.clear();
      return;
    }

    if (startIndex >= state.filteredTenants.length) {
      state.paginatedTenants.clear();
      return;
    }

    final actualEndIndex = endIndex > state.filteredTenants.length ? state.filteredTenants.length : endIndex;

    state.paginatedTenants.value = state.filteredTenants.sublist(startIndex, actualEndIndex);
  }

  void goToPage(int page) {
    if (page < 1 || page > state.totalPages.value) return;

    state.currentPage.value = page;
    updatePaginatedData();
  }

  void nextPage() {
    if (state.currentPage.value < state.totalPages.value) {
      state.currentPage.value++;
      updatePaginatedData();
    }
  }

  void previousPage() {
    if (state.currentPage.value > 1) {
      state.currentPage.value--;
      updatePaginatedData();
    }
  }

  void changePageSize(int size) {
    state.pageSize.value = size;
    updatePagination();
  }

  void toggleColumnVisibility(String columnName) {
    if (state.visibleColumns.contains(columnName)) {
      if (state.visibleColumns.length > 1) {
        // Ensure at least one column is visible
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

  void sortByColumn(int columnIndex, bool ascending) {
    // If we're already sorting by this column, just toggle the direction
    if (state.sortColumnIndex.value == columnIndex) {
      state.sortAscending.value = !state.sortAscending.value;
    } else {
      // Otherwise, set the new sort column and default to ascending
      state.sortColumnIndex.value = columnIndex;
      state.sortAscending.value = ascending;
    }

    _sortTenants();
    updatePaginatedData();
  }

  void _sortTenants() {
    if (state.sortColumnIndex.value == null) return;

    final columnIndex = state.sortColumnIndex.value!;
    final ascending = state.sortAscending.value;
    final visibleColumns = _getVisibleColumnsList();

    if (columnIndex >= visibleColumns.length) return;

    final columnName = visibleColumns[columnIndex];

    state.filteredTenants.sort((a, b) {
      var aValue, bValue;

      // Get the values to compare based on column name
      switch (columnName) {
        case 'tenantId':
          aValue = a.tenantId;
          bValue = b.tenantId;
          break;
        case 'tenantName':
          aValue = a.tenantName;
          bValue = b.tenantName;
          break;
        case 'createdAt':
          aValue = a.createdAt;
          bValue = b.createdAt;
          break;
        case 'creator':
          aValue = a.creator;
          bValue = b.creator;
          break;
        case 'domainUrl':
          aValue = a.domainUrl;
          bValue = b.domainUrl;
          break;
        case 'description':
          aValue = a.description;
          bValue = b.description;
          break;
        case 'contactEmail':
          aValue = a.contactEmail;
          bValue = b.contactEmail;
          break;
        case 'contactPhone':
          aValue = a.contactPhone;
          bValue = b.contactPhone;
          break;
        case 'industry':
          aValue = a.industry;
          bValue = b.industry;
          break;
        case 'subscriptionType':
          aValue = a.subscriptionType;
          bValue = b.subscriptionType;
          break;
        case 'status':
          aValue = a.status;
          bValue = b.status;
          break;
        default:
          return 0;
      }

      // Compare the values
      int result;
      if (aValue is String && bValue is String) {
        result = aValue.compareTo(bValue);
      } else if (aValue is DateTime && bValue is DateTime) {
        result = aValue.compareTo(bValue);
      } else if (aValue is num && bValue is num) {
        result = aValue.compareTo(bValue);
      } else {
        result = 0;
      }

      // Apply sort direction
      return ascending ? result : -result;
    });
  }

  List<String> _getVisibleColumnsList() {
    final List<String> columns = [];

    // Always visible columns
    if (isColumnVisible('tenantId')) {
      columns.add('tenantId');
    }

    if (isColumnVisible('tenantName')) {
      columns.add('tenantName');
    }

    if (isColumnVisible('createdAt')) {
      columns.add('createdAt');
    }

    // Optional columns
    if (isColumnVisible('creator')) {
      columns.add('creator');
    }

    if (isColumnVisible('domainUrl')) {
      columns.add('domainUrl');
    }

    if (isColumnVisible('description')) {
      columns.add('description');
    }

    if (isColumnVisible('contactEmail')) {
      columns.add('contactEmail');
    }

    if (isColumnVisible('contactPhone')) {
      columns.add('contactPhone');
    }

    if (isColumnVisible('industry')) {
      columns.add('industry');
    }

    if (isColumnVisible('subscriptionType')) {
      columns.add('subscriptionType');
    }

    // Status column is always visible
    columns.add('status');

    return columns;
  }

  void _loadMockData() {
    // Generate 50 mock tenants to demonstrate pagination
    final List<TenantModel> mockTenants = [];

    final industries = ['Technology', 'Healthcare', 'Finance', 'Education', 'Manufacturing', 'Retail', 'Transportation'];
    final subscriptionTypes = ['Basic', 'Standard', 'Premium', 'Enterprise'];

    for (int i = 1; i <= 550; i++) {
      final tenantId = 'TN${i.toString().padLeft(3, '0')}';
      final status = i % 3 == 0 ? 'ACTIVE' : (i % 3 == 1 ? 'CREATED' : 'IN-ACTIVE');
      final industry = industries[i % industries.length];
      final subscriptionType = subscriptionTypes[i % subscriptionTypes.length];

      mockTenants.add(
        TenantModel(
          tenantId: tenantId,
          tenantName: 'Tenant $i ${industry} Corp',
          createdAt: DateTime(2023, (i % 12) + 1, (i % 28) + 1),
          creator: 'Admin ${i % 5 + 1}',
          domainUrl: 'tenant$i.example.com',
          status: status,
          description: 'This is a ${industry.toLowerCase()} company with ${subscriptionType.toLowerCase()} subscription',
          contactEmail: 'contact@tenant$i.example.com',
          contactPhone: '+1-555-${i.toString().padLeft(3, '0')}-${(i * 7).toString().padLeft(4, '0')}',
          address: '${i * 100} Main St, Suite ${i * 10}, City ${i % 10 + 1}',
          usersCount: i * 5 + 10,
          industry: industry,
          subscriptionType: subscriptionType,
          lastLoginAt: DateTime.now().subtract(Duration(days: i % 30, hours: i % 24)),
        ),
      );
    }

    state.tenants.addAll(mockTenants);
  }
}
