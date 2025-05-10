import 'package:flutter/material.dart';
import 'package:get/get.dart';
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
          Expanded(
            child: Card(
              elevation: 2,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Obx(
                  () => Column(
                    children: [
                      Expanded(
                        child: Align(
                          alignment: Alignment.centerLeft,
                          child: SingleChildScrollView(
                            physics: AlwaysScrollableScrollPhysics(),
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
                          ),
                        ),
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.end,
                        children: [
                          Row(children: [_buildPaginationControls(), SizedBox(width: 16), _buildColumnOptionsMenu()]),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPageHeader() {
    return Text('Tenant Management', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: AppColors.colorPrimary01));
  }

  Widget _buildBreadcrumbs() {
    return Row(
      children: [
        InkWell(
          onTap: () => Get.toNamed('/home'),
          child: Text('Home', style: TextStyle(color: AppColors.colorPrimary01, fontWeight: FontWeight.w500)),
        ),
        Padding(padding: EdgeInsets.symmetric(horizontal: 8.0), child: Icon(Icons.chevron_right, size: 16, color: Colors.grey)),
        Text('Tenant Management', style: TextStyle(color: Colors.grey, fontWeight: FontWeight.w500)),
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
                    Text('Filters', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                    Icon(controller.state.isFilterPanelExpanded.value ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down),
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
                            decoration: InputDecoration(labelText: 'Status', border: OutlineInputBorder()),
                            value: controller.state.selectedStatus.value,
                            items: [
                              DropdownMenuItem<String>(value: null, child: Text('All')),
                              ...controller.state.availableStatuses.map((status) {
                                return DropdownMenuItem<String>(value: status, child: Text(status));
                              }),
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
                          style: ElevatedButton.styleFrom(backgroundColor: AppColors.colorPrimary01, foregroundColor: Colors.white),
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
                  Text('Tenants (${controller.state.filteredTenants.length})', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18)),
                  Row(children: [_buildPaginationControls(), SizedBox(width: 16), _buildColumnOptionsMenu()]),
                ],
              ),
              SizedBox(height: 16),
              SingleChildScrollView(
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
              ),
            ],
          ),
        ),
      );
    });
  }

  Widget _buildPaginationControls() {
    return Row(
      children: [
        // Page size dropdown
        Container(
          padding: EdgeInsets.symmetric(horizontal: 8),
          decoration: BoxDecoration(border: Border.all(color: Colors.grey.shade300), borderRadius: BorderRadius.circular(4)),
          child: DropdownButton<int>(
            value: controller.state.pageSize.value,
            underline: SizedBox(),
            items:
                controller.state.pageSizeOptions.map((size) {
                  return DropdownMenuItem<int>(value: size, child: Text('$size items'));
                }).toList(),
            onChanged: (value) {
              if (value != null) {
                controller.changePageSize(value);
              }
            },
          ),
        ),
        SizedBox(width: 16),
        // Pagination buttons
        IconButton(
          icon: Icon(Icons.first_page),
          onPressed: controller.state.currentPage.value > 1 ? () => controller.goToPage(1) : null,
          tooltip: 'First Page',
        ),
        IconButton(
          icon: Icon(Icons.navigate_before),
          onPressed: controller.state.currentPage.value > 1 ? () => controller.previousPage() : null,
          tooltip: 'Previous Page',
        ),
        Container(
          padding: EdgeInsets.symmetric(horizontal: 12),
          child: Text('${controller.state.currentPage.value} / ${controller.state.totalPages.value}', style: TextStyle(fontWeight: FontWeight.bold)),
        ),
        IconButton(
          icon: Icon(Icons.navigate_next),
          onPressed: controller.state.currentPage.value < controller.state.totalPages.value ? () => controller.nextPage() : null,
          tooltip: 'Next Page',
        ),
        IconButton(
          icon: Icon(Icons.last_page),
          onPressed:
              controller.state.currentPage.value < controller.state.totalPages.value
                  ? () => controller.goToPage(controller.state.totalPages.value)
                  : null,
          tooltip: 'Last Page',
        ),
      ],
    );
  }

  Widget _buildColumnOptionsMenu() {
    return PopupMenuButton<String>(
      icon: Row(mainAxisSize: MainAxisSize.min, children: [Icon(Icons.view_column), SizedBox(width: 4), Text('More')]),
      onSelected: (String columnName) {
        controller.toggleColumnVisibility(columnName);
      },
      itemBuilder:
          (BuildContext context) => <PopupMenuEntry<String>>[
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
            PopupMenuItem<String>(
              value: 'description',
              child: Row(
                children: [
                  Checkbox(
                    value: controller.isColumnVisible('description'),
                    onChanged: (bool? value) {
                      controller.toggleColumnVisibility('description');
                      Navigator.pop(context);
                    },
                  ),
                  Text('Description'),
                ],
              ),
            ),
            PopupMenuItem<String>(
              value: 'contactEmail',
              child: Row(
                children: [
                  Checkbox(
                    value: controller.isColumnVisible('contactEmail'),
                    onChanged: (bool? value) {
                      controller.toggleColumnVisibility('contactEmail');
                      Navigator.pop(context);
                    },
                  ),
                  Text('Contact Email'),
                ],
              ),
            ),
            PopupMenuItem<String>(
              value: 'contactPhone',
              child: Row(
                children: [
                  Checkbox(
                    value: controller.isColumnVisible('contactPhone'),
                    onChanged: (bool? value) {
                      controller.toggleColumnVisibility('contactPhone');
                      Navigator.pop(context);
                    },
                  ),
                  Text('Contact Phone'),
                ],
              ),
            ),
            PopupMenuItem<String>(
              value: 'industry',
              child: Row(
                children: [
                  Checkbox(
                    value: controller.isColumnVisible('industry'),
                    onChanged: (bool? value) {
                      controller.toggleColumnVisibility('industry');
                      Navigator.pop(context);
                    },
                  ),
                  Text('Industry'),
                ],
              ),
            ),
            PopupMenuItem<String>(
              value: 'subscriptionType',
              child: Row(
                children: [
                  Checkbox(
                    value: controller.isColumnVisible('subscriptionType'),
                    onChanged: (bool? value) {
                      controller.toggleColumnVisibility('subscriptionType');
                      Navigator.pop(context);
                    },
                  ),
                  Text('Subscription Type'),
                ],
              ),
            ),
          ],
    );
  }

  Widget _buildDataTable() {
    return Container(
      width: 400,
      height: 500,
      decoration: BoxDecoration(border: Border.all(color: Colors.grey.shade300), borderRadius: BorderRadius.circular(4)),
      child: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child: SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: ConstrainedBox(
            constraints: BoxConstraints(minWidth: double.infinity),
            child: DataTable(
              columns: _buildDataColumns(),
              rows: _buildDataRows(),
              columnSpacing: 20,
              headingRowColor: MaterialStateProperty.all(Colors.grey.shade100),
              dataRowMinHeight: 48,
              dataRowMaxHeight: 64,
            ),
          ),
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

    if (controller.isColumnVisible('description')) {
      columns.add(DataColumn(label: Text('Description', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('contactEmail')) {
      columns.add(DataColumn(label: Text('Contact Email', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('contactPhone')) {
      columns.add(DataColumn(label: Text('Contact Phone', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('industry')) {
      columns.add(DataColumn(label: Text('Industry', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    if (controller.isColumnVisible('subscriptionType')) {
      columns.add(DataColumn(label: Text('Subscription Type', style: TextStyle(fontWeight: FontWeight.bold))));
    }

    // Status column
    columns.add(DataColumn(label: Text('Status', style: TextStyle(fontWeight: FontWeight.bold))));

    return columns;
  }

  List<DataRow> _buildDataRows() {
    return controller.state.paginatedTenants.map((tenant) {
      return DataRow(
        cells: [
          if (controller.isColumnVisible('tenantId')) DataCell(Text(tenant.tenantId)),

          if (controller.isColumnVisible('tenantName')) DataCell(Text(tenant.tenantName)),

          if (controller.isColumnVisible('createdAt')) DataCell(Text(controller.formatDate(tenant.createdAt))),

          if (controller.isColumnVisible('creator')) DataCell(Text(tenant.creator)),

          if (controller.isColumnVisible('domainUrl')) DataCell(Text(tenant.domainUrl)),

          if (controller.isColumnVisible('description')) DataCell(Text(tenant.description)),

          if (controller.isColumnVisible('contactEmail')) DataCell(Text(tenant.contactEmail)),

          if (controller.isColumnVisible('contactPhone')) DataCell(Text(tenant.contactPhone)),

          if (controller.isColumnVisible('industry')) DataCell(Text(tenant.industry)),

          if (controller.isColumnVisible('subscriptionType')) DataCell(Text(tenant.subscriptionType)),

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
      label: Text(status, style: TextStyle(color: Colors.white, fontSize: 12)),
      backgroundColor: chipColor,
      padding: EdgeInsets.symmetric(horizontal: 4),
      labelPadding: EdgeInsets.symmetric(horizontal: 4),
    );
  }
}
