import 'dart:developer';

import 'package:collection/collection.dart';
import 'package:containerbase/pages/tenant_management/state.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';

import '../../commons/constants/colors.dart';
import '../../commons/widgets/content_layout/view.dart';
import '../../commons/widgets/vinta_paging_datatable/models.dart';
import '../../commons/widgets/vinta_paging_datatable/view.dart';
import '../../services/navigation/constants.dart';
import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class TenantManagementPage extends AppPage<TenantManagementPageController> {
  TenantManagementPage({super.key}) {
    TenantManagementPageBindings().dependencies();
  }

  // Add scroll controllers
  final ScrollController _verticalScrollController = ScrollController();
  final ScrollController _bodyHorizontalController = ScrollController();
  final ScrollController _headerHorizontalController = ScrollController();

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Tenant Management',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.tenantManagement, AppNavigationItemConfig.userManagement],
      content: [
        _buildFilterPanel(),
        AppSpaces.spaceH16,
        Expanded(
          child: Card(
            elevation: 2,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(
                () => VintaPagingDataTable<TenantModel>(
                  verticalScrollController: _verticalScrollController,
                  bodyHorizontalScrollController: _bodyHorizontalController,
                  headerHorizontalScrollController: _headerHorizontalController,
                  dataFilter: controller.state.tenantFilter.value,
                  // dataSorter: controller.state.tenantFilter,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'Tenant ID', columnKey: 'tenantId', isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Tenant Name', columnKey: 'tenantName'),
                    DataColumnSetting(index: 2, label: 'Created At', columnKey: 'createdAt'),
                    DataColumnSetting(index: 3, label: 'Creator', columnKey: 'creator'),
                    DataColumnSetting(index: 4, label: 'Domain URL', columnKey: 'domainUrl'),
                    DataColumnSetting(index: 5, label: 'Description', columnKey: 'description'),
                    DataColumnSetting(index: 6, label: 'Contact Email', columnKey: 'contactEmail'),
                    DataColumnSetting(index: 7, label: 'Contact Phone', columnKey: 'contactPhone'),
                    DataColumnSetting(index: 8, label: 'Industry', columnKey: 'industry'),
                    DataColumnSetting(index: 9, label: 'Subscription Type', columnKey: 'subscriptionType'),
                    DataColumnSetting(index: 10, label: 'Status', columnKey: 'status'),
                  ],
                  dataRowBuilder: (tenant, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          switch (column.columnKey) {
                            case 'tenantId':
                              return DataCell(Text(tenant.tenantId));
                            case 'tenantName':
                              return DataCell(Text(tenant.tenantName));
                            case 'createdAt':
                              return DataCell(Text(controller.formatDate(tenant.createdAt)));
                            case 'creator':
                              return DataCell(Text(tenant.creator));
                            case 'domainUrl':
                              return DataCell(Text(tenant.domainUrl));
                            case 'description':
                              return DataCell(Text(tenant.description));
                            case 'contactEmail':
                              return DataCell(Text(tenant.contactEmail));
                            case 'contactPhone':
                              return DataCell(Text(tenant.contactPhone));
                            case 'industry':
                              return DataCell(Text(tenant.industry));
                            case 'subscriptionType':
                              return DataCell(Text(tenant.subscriptionType));
                            case 'status':
                              return DataCell(_buildStatusChip(tenant.status));
                            default:
                              return DataCell(Text('-'));
                          }
                        }).toList();

                    return DataRow(cells: cells);
                  },
                  dataLoader: (filterRequest, pageRequest) {
                    final tenants = controller.state.tenants.sublist(pageRequest.page * pageRequest.size, (pageRequest.page + 1) * pageRequest.size);
                    log("Loaded ${tenants.length} tenants");
                    return Future.delayed(
                      Duration(milliseconds: 500),
                      () => PagingResponse(
                        content: tenants,
                        page: pageRequest.page,
                        totalElements: controller.state.tenants.length,
                        totalPages: (controller.state.tenants.length / pageRequest.size).ceil(),
                      ),
                    );
                  },
                ),
              ),
            ),
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

  List<DataColumn> _buildDataColumns() {
    final List<DataColumn> columns = [];
    int columnIndex = 0;

    // Always visible columns
    if (controller.isColumnVisible('tenantId')) {
      columns.add(_buildSortableColumn('Tenant ID'));
    }

    if (controller.isColumnVisible('tenantName')) {
      columns.add(_buildSortableColumn('Tenant Name'));
    }

    if (controller.isColumnVisible('createdAt')) {
      columns.add(_buildSortableColumn('Created At'));
    }

    // Optional columns
    if (controller.isColumnVisible('creator')) {
      columns.add(_buildSortableColumn('Creator'));
    }

    if (controller.isColumnVisible('domainUrl')) {
      columns.add(_buildSortableColumn('Domain URL'));
    }

    if (controller.isColumnVisible('description')) {
      columns.add(_buildSortableColumn('Description'));
    }

    if (controller.isColumnVisible('contactEmail')) {
      columns.add(_buildSortableColumn('Contact Email'));
    }

    if (controller.isColumnVisible('contactPhone')) {
      columns.add(_buildSortableColumn('Contact Phone'));
    }

    if (controller.isColumnVisible('industry')) {
      columns.add(_buildSortableColumn('Industry'));
    }

    if (controller.isColumnVisible('subscriptionType')) {
      columns.add(_buildSortableColumn('Subscription Type'));
    }

    // Status column
    columns.add(_buildSortableColumn('Status'));

    return columns;
  }

  DataColumn _buildSortableColumn(String label) {
    return DataColumn(
      label: Container(padding: EdgeInsets.symmetric(vertical: 8.0), child: Text(label, style: TextStyle(fontWeight: FontWeight.bold))),
      onSort: (columnIndex, ascending) {
        controller.sortByColumn(columnIndex, ascending);
      },
    );
  }

  List<Widget> _buildHeaderRow() {
    final List<Widget> headerCells = [];
    int columnIndex = 0;
    final double columnSpacing = 20;
    final double cellPadding = 16;

    // Function to create a header cell
    Widget createHeaderCell(String label, String columnName, int index) {
      final bool isCurrentSortColumn = controller.state.sortColumnIndex.value == index;
      final bool isAscending = controller.state.sortAscending.value;

      return InkWell(
        onTap: () {
          if (isCurrentSortColumn) {
            controller.sortByColumn(index, !isAscending);
          } else {
            controller.sortByColumn(index, true);
          }
        },
        child: Container(
          padding: EdgeInsets.symmetric(horizontal: cellPadding, vertical: 16),
          decoration: BoxDecoration(
            border: Border(right: BorderSide(color: Colors.grey.shade300, width: 1), bottom: BorderSide(color: Colors.grey.shade300, width: 1)),
          ),
          child: Row(
            children: [
              Text(label, style: TextStyle(fontWeight: FontWeight.bold)),
              SizedBox(width: 4),
              if (isCurrentSortColumn) Icon(isAscending ? Icons.arrow_upward : Icons.arrow_downward, size: 16, color: Colors.grey.shade700),
            ],
          ),
        ),
      );
    }

    // Always visible columns
    if (controller.isColumnVisible('tenantId')) {
      headerCells.add(createHeaderCell('Tenant ID', 'tenantId', columnIndex++));
    }

    if (controller.isColumnVisible('tenantName')) {
      headerCells.add(createHeaderCell('Tenant Name', 'tenantName', columnIndex++));
    }

    if (controller.isColumnVisible('createdAt')) {
      headerCells.add(createHeaderCell('Created At', 'createdAt', columnIndex++));
    }

    // Optional columns
    if (controller.isColumnVisible('creator')) {
      headerCells.add(createHeaderCell('Creator', 'creator', columnIndex++));
    }

    if (controller.isColumnVisible('domainUrl')) {
      headerCells.add(createHeaderCell('Domain URL', 'domainUrl', columnIndex++));
    }

    if (controller.isColumnVisible('description')) {
      headerCells.add(createHeaderCell('Description', 'description', columnIndex++));
    }

    if (controller.isColumnVisible('contactEmail')) {
      headerCells.add(createHeaderCell('Contact Email', 'contactEmail', columnIndex++));
    }

    if (controller.isColumnVisible('contactPhone')) {
      headerCells.add(createHeaderCell('Contact Phone', 'contactPhone', columnIndex++));
    }

    if (controller.isColumnVisible('industry')) {
      headerCells.add(createHeaderCell('Industry', 'industry', columnIndex++));
    }

    if (controller.isColumnVisible('subscriptionType')) {
      headerCells.add(createHeaderCell('Subscription Type', 'subscriptionType', columnIndex++));
    }

    // Status column
    headerCells.add(createHeaderCell('Status', 'status', columnIndex++));

    return headerCells;
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
