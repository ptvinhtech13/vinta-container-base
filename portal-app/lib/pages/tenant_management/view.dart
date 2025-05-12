import 'package:collection/collection.dart';
import 'package:data_table_2/data_table_2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';
import 'package:vinta_shared_commons/utils/app_utils.dart';

import '../../commons/widgets/content_layout/view.dart';
import '../../commons/widgets/vinta_paging_datatable/models.dart';
import '../../commons/widgets/vinta_paging_datatable/view.dart';
import '../../services/navigation/constants.dart';
import '../../services/tenant/models.dart';
import '../../services/tenant/requests.dart';
import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class TenantManagementPage extends AppPage<TenantManagementPageController> {
  TenantManagementPage({super.key}) {
    TenantManagementPageBindings().dependencies();
  }

  final ScrollController _scrollController = ScrollController();

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Tenant Management',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.tenantManagement],
      content: [
        // _buildFilterPanel(),
        AppSpaces.spaceH16,
        Expanded(
          child: Card(
            elevation: 2,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(
                () => VintaPagingDataTable<TenantModel, TenantFilter>(
                  scrollController: _scrollController,
                  filter: controller.state.filter,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'Tenant ID', columnKey: 'id', size: ColumnSize.M, isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Title', size: ColumnSize.M, columnKey: 'title', isSortable: true),
                    DataColumnSetting(index: 2, label: 'Status', size: ColumnSize.S, columnKey: 'status', isSortable: false),
                    DataColumnSetting(index: 3, label: 'Description', size: ColumnSize.M, columnKey: 'description', isSortable: false),
                    DataColumnSetting(index: 4, label: 'Domain URL', size: ColumnSize.M, columnKey: 'domainHost', isSortable: false),
                    DataColumnSetting(index: 5, size: ColumnSize.S, label: 'Created At', columnKey: 'createdAt', isSortable: true),
                  ],
                  dataRowBuilder: (tenant, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          final Widget child = switch (column.columnKey) {
                            'id' => SelectableText(tenant.id, style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold)),
                            'title' => SelectableText(tenant.title, style: TextStyle(fontSize: 14)),
                            'status' => _buildStatusChip(tenant.status),
                            'description' => SelectableText(tenant.description ?? '-', style: TextStyle(fontSize: 14)),
                            'domainHost' => SelectableText(tenant.domainHost, style: TextStyle(fontSize: 14)),
                            'createdAt' => SelectableText(
                              AppUtils.formatDateTime(tenant.createdAt, isAlreadyLocal: false),
                              style: TextStyle(fontSize: 14),
                            ),
                            _ => SelectableText('-'),
                          };
                          return DataCell(child);
                        }).toList();

                    return DataRow(cells: cells);
                  },
                  dataLoader: (pageRequest) => controller.queryTenants(pageRequest),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildStatusChip(TenantStatus status) {
    Color chipColor;
    switch (status) {
      case TenantStatus.ACTIVE:
        chipColor = Colors.green;
        break;
      case TenantStatus.CREATED:
        chipColor = Colors.blue;
        break;
      case TenantStatus.INACTIVE:
        chipColor = Colors.red;
    }

    return Chip(
      label: Text(status.name, style: TextStyle(color: Colors.white, fontSize: 12)),
      backgroundColor: chipColor,
      padding: EdgeInsets.symmetric(horizontal: 4),
      labelPadding: EdgeInsets.symmetric(horizontal: 4),
    );
  }
}
