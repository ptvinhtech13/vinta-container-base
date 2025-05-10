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

  // Add scroll controllers
  final ScrollController _verticalScrollController = ScrollController();
  final ScrollController _bodyHorizontalController = ScrollController();
  final ScrollController _headerHorizontalController = ScrollController();

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
                          alignment: Alignment.topLeft,
                          child: Column(
                            children: [
                              // Sticky header
                              Container(
                                color: Colors.grey.shade100,
                                child: SingleChildScrollView(
                                  controller: _headerHorizontalController,
                                  scrollDirection: Axis.horizontal,
                                  child: Row(children: _buildHeaderRow()),
                                ),
                              ),
                              // Table body with scrolling
                              Expanded(
                                child: Scrollbar(
                                  thumbVisibility: true,
                                  controller: _verticalScrollController,
                                  child: SingleChildScrollView(
                                    physics: AlwaysScrollableScrollPhysics(),
                                    controller: _verticalScrollController,
                                    scrollDirection: Axis.vertical,
                                    child: Scrollbar(
                                      thumbVisibility: true,
                                      controller: _bodyHorizontalController,
                                      scrollbarOrientation: ScrollbarOrientation.bottom,
                                      child: SingleChildScrollView(
                                        controller: _bodyHorizontalController,
                                        scrollDirection: Axis.horizontal,
                                        child: DataTable(
                                          columns: _buildDataColumns(),
                                          rows: _buildDataRows(),
                                          columnSpacing: 20,
                                          headingRowColor: MaterialStateProperty.all(Colors.grey.shade100),
                                          dataRowMinHeight: 48,
                                          dataRowMaxHeight: 64,
                                          sortColumnIndex: controller.state.sortColumnIndex.value,
                                          sortAscending: controller.state.sortAscending.value,
                                          headingRowHeight: 0,
                                          // Hide the original header
                                          border: TableBorder(
                                            bottom: BorderSide(color: Colors.grey.shade300),
                                            horizontalInside: BorderSide(color: Colors.grey.shade200),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ),
                              ),
                            ],
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
              // Use separate controllers for this table if it's ever used
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
                    sortColumnIndex: controller.state.sortColumnIndex.value,
                    sortAscending: controller.state.sortAscending.value,
                    headingRowHeight: 56,
                    border: TableBorder(bottom: BorderSide(color: Colors.grey.shade300), horizontalInside: BorderSide(color: Colors.grey.shade200)),
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
    // Create separate controllers for this table if it's ever used
    final ScrollController verticalController = ScrollController();
    final ScrollController horizontalController = ScrollController();

    return Container(
      width: 400,
      height: 500,
      decoration: BoxDecoration(border: Border.all(color: Colors.grey.shade300), borderRadius: BorderRadius.circular(4)),
      child: Column(
        children: [
          // Sticky header
          Container(
            color: Colors.grey.shade100,
            child: SingleChildScrollView(
              controller: horizontalController,
              scrollDirection: Axis.horizontal,
              child: Row(children: _buildHeaderRow()),
            ),
          ),
          // Table body
          Expanded(
            child: Scrollbar(
              thumbVisibility: true,
              controller: verticalController,
              child: SingleChildScrollView(
                controller: verticalController,
                scrollDirection: Axis.vertical,
                child: Scrollbar(
                  thumbVisibility: true,
                  controller: horizontalController,
                  scrollbarOrientation: ScrollbarOrientation.bottom,
                  child: SingleChildScrollView(
                    controller: horizontalController,
                    scrollDirection: Axis.horizontal,
                    child: DataTable(
                      columns: _buildDataColumns(),
                      rows: _buildDataRows(),
                      columnSpacing: 20,
                      headingRowColor: MaterialStateProperty.all(Colors.grey.shade100),
                      dataRowMinHeight: 48,
                      dataRowMaxHeight: 64,
                      sortColumnIndex: controller.state.sortColumnIndex.value,
                      sortAscending: controller.state.sortAscending.value,
                      headingRowHeight: 0, // Hide the original header
                      border: TableBorder(
                        bottom: BorderSide(color: Colors.grey.shade300),
                        horizontalInside: BorderSide(color: Colors.grey.shade200),
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  List<DataColumn> _buildDataColumns() {
    final List<DataColumn> columns = [];
    int columnIndex = 0;

    // Always visible columns
    if (controller.isColumnVisible('tenantId')) {
      columns.add(_buildSortableColumn('Tenant ID', 'tenantId', columnIndex++));
    }

    if (controller.isColumnVisible('tenantName')) {
      columns.add(_buildSortableColumn('Tenant Name', 'tenantName', columnIndex++));
    }

    if (controller.isColumnVisible('createdAt')) {
      columns.add(_buildSortableColumn('Created At', 'createdAt', columnIndex++));
    }

    // Optional columns
    if (controller.isColumnVisible('creator')) {
      columns.add(_buildSortableColumn('Creator', 'creator', columnIndex++));
    }

    if (controller.isColumnVisible('domainUrl')) {
      columns.add(_buildSortableColumn('Domain URL', 'domainUrl', columnIndex++));
    }

    if (controller.isColumnVisible('description')) {
      columns.add(_buildSortableColumn('Description', 'description', columnIndex++));
    }

    if (controller.isColumnVisible('contactEmail')) {
      columns.add(_buildSortableColumn('Contact Email', 'contactEmail', columnIndex++));
    }

    if (controller.isColumnVisible('contactPhone')) {
      columns.add(_buildSortableColumn('Contact Phone', 'contactPhone', columnIndex++));
    }

    if (controller.isColumnVisible('industry')) {
      columns.add(_buildSortableColumn('Industry', 'industry', columnIndex++));
    }

    if (controller.isColumnVisible('subscriptionType')) {
      columns.add(_buildSortableColumn('Subscription Type', 'subscriptionType', columnIndex++));
    }

    // Status column
    columns.add(_buildSortableColumn('Status', 'status', columnIndex++));

    return columns;
  }

  DataColumn _buildSortableColumn(String label, String columnName, int index) {
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
