import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';

import '../../commons/constants/colors.dart';
import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class TenantManagementPage extends AppPage<TenantManagementPageController> {
  TenantManagementPage({super.key}) {
    TenantManagementPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _buildPageHeader(),
          AppSpaces.spaceH16,
          _buildBreadcrumbs(),
          AppSpaces.spaceH16,
          _buildFilterPanel(),
          AppSpaces.spaceH16,
          _buildTenantTable(),
        ],
      ),
    );
  }

  Widget _buildPageHeader() {
    return Text(
      'Tenant Management',
      style: TextStyle(
        fontSize: 24,
        fontWeight: FontWeight.bold,
        color: AppColors.colorPrimary01,
      ),
    );
  }

  Widget _buildBreadcrumbs() {
    return Row(
      children: [
        InkWell(
          onTap: () => Get.toNamed('/home'),
          child: Text(
            'Home',
            style: TextStyle(
              color: AppColors.colorPrimary01,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 8.0),
          child: Icon(Icons.chevron_right, size: 16, color: Colors.grey),
        ),
        Text(
          'Tenant Management',
          style: TextStyle(
            color: Colors.grey,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }

  Widget _buildFilterPanel() {
    return Obx(() {
      return Card(
        elevation: 2,
        child: Column(
          children: [
            // Filter panel header with toggle button
            InkWell(
              onTap: controller.toggleFilterPanel,
              child: Padding(
                padding: EdgeInsets.all(12.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      'Filters',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    ),
                    Icon(
                      controller.state.isFilterPanelExpanded.value
                          ? Icons.keyboard_arrow_up
                          : Icons.keyboard_arrow_down,
                    ),
                  ],
                ),
              ),
            ),
            // Filter panel content
            if (controller.state.isFilterPanelExpanded.value)
              Padding(
                padding: EdgeInsets.fromLTRB(12.0, 0, 12.0, 12.0),
                child: Column(
                  children: [
                    Row(
                      children: [
                        // Search field
                        Expanded(
                          flex: 3,
                          child: TextField(
                            decoration: InputDecoration(
                              hintText: 'Search by tenant name',
                              prefixIcon: Icon(Icons.search),
                              border: OutlineInputBorder(),
                            ),
                            onChanged: (value) {
                              controller.state.searchText.value = value;
                              controller.filterTenants();
                            },
                          ),
                        ),
                        SizedBox(width: 16),
                        // Status dropdown
                        Expanded(
                          flex: 2,
                          child: DropdownButtonFormField<String>(
                            decoration: InputDecoration(
                              labelText: 'Status',
                              border: OutlineInputBorder(),
                            ),
                            value: controller.state.selectedStatus.value,
                            items: [
                              DropdownMenuItem<String>(
                                value: null,
                                child: Text('All'),
                              ),
                              ...controller.state.availableStatuses.map((status) {
                                return DropdownMenuItem<String>(
                                  value: status,
                                  child: Text(status),
                                );
                              }).toList(),
                            ],
                            onChanged: (value) {
                              controller.state.selectedStatus.value = value;
                              controller.filterTenants();
                            },
                          ),
                        ),
                        SizedBox(width: 16),
                        // Clear filters button
                        ElevatedButton.icon(
                          onPressed: controller.clearFilters,
                          icon: Icon(Icons.clear_all),
                          label: Text('Clear'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: AppColors.colorPrimary01,
                            foregroundColor: Colors.white,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
          ],
        ),
      );
    });
  }

  Widget _buildTenantTable() {
    return Obx(() {
      return Card(
        elevation: 2,
        child: Padding(
          padding: EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Tenants (${controller.state.filteredTenants.length})',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 18,
                    ),
                  ),
                  _buildColumnOptionsMenu(),
                ],
              ),
              SizedBox(height: 16),
              _buildDataTable(),
            ],
          ),
        ),
      );
    });
  }

  Widget _buildColumnOptionsMenu() {
    return PopupMenuButton<String>(
      icon: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(Icons.view_column),
          SizedBox(width: 4),
          Text('More'),
        ],
      ),
      onSelected: (String columnName) {
        controller.toggleColumnVisibility(columnName);
      },
      itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
        PopupMenuItem<String>(
          value: 'creator',
          child: Row(
            children: [
              Checkbox(
                value: controller.isColumnVisible('creator'),
                onChanged: (bool? value) {
                  controller.toggleColumnVisibility('creator');
                  Navigator.pop(context);
                },
              ),
              Text('Creator'),
            ],
          ),
        ),
        PopupMenuItem<String>(
          value: 'domainUrl',
          child: Row(
            children: [
              Checkbox(
                value: controller.isColumnVisible('domainUrl'),
                onChanged: (bool? value) {
                  controller.toggleColumnVisibility('domainUrl');
                  Navigator.pop(context);
                },
              ),
              Text('Domain URL'),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildDataTable() {
    return SingleChildScrollView(
      scrollDirection: Axis.vertical,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: DataTable(
          columns: _buildDataColumns(),
          rows: _buildDataRows(),
          columnSpacing: 20,
          headingRowColor: MaterialStateProperty.all(Colors.grey.shade100),
          dataRowMinHeight: 48,
          dataRowMaxHeight: 64,
        ),
      ),
    );
  }

  List<DataColumn> _buildDataColumns() {
    final List<DataColumn> columns = [];

    // Always visible columns
    if (controller.isColumnVisible('tenantId')) {
      columns.add(DataColumn(label: Text('Tenant ID', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('tenantName')) {
      columns.add(DataColumn(label: Text('Tenant Name', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('createdAt')) {
      columns.add(DataColumn(label: Text('Created At', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    // Optional columns
    if (controller.isColumnVisible('creator')) {
      columns.add(DataColumn(label: Text('Creator', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('domainUrl')) {
      columns.add(DataColumn(label: Text('Domain URL', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    // Status column
    columns.add(DataColumn(label: Text('Status', style: TextStyle(fontWeight: FontWeight.bold))));

    return columns;
  }

  List<DataRow> _buildDataRows() {
    return controller.state.filteredTenants.map((tenant) {
      return DataRow(
        cells: [
          if (controller.isColumnVisible('tenantId'))
            DataCell(Text(tenant.tenantId)),

          if (controller.isColumnVisible('tenantName'))
            DataCell(Text(tenant.tenantName)),

          if (controller.isColumnVisible('createdAt'))
            DataCell(Text(controller.formatDate(tenant.createdAt))),

          if (controller.isColumnVisible('creator'))
            DataCell(Text(tenant.creator)),

          if (controller.isColumnVisible('domainUrl'))
            DataCell(Text(tenant.domainUrl)),

          DataCell(_buildStatusChip(tenant.status)),
        ],
      );
    }).toList();
  }

  Widget _buildStatusChip(String status) {
    Color chipColor;
    switch (status) {
      case 'ACTIVE':
        chipColor = Colors.green;
        break;
      case 'CREATED':
        chipColor = Colors.blue;
        break;
      case 'IN-ACTIVE':
        chipColor = Colors.red;
        break;
      default:
        chipColor = Colors.grey;
    }

    return Chip(
      label: Text(
        status,
        style: TextStyle(color: Colors.white, fontSize: 12),
      ),
      backgroundColor: chipColor,
      padding: EdgeInsets.symmetric(horizontal: 4),
      labelPadding: EdgeInsets.symmetric(horizontal: 4),
    );
  }
}
