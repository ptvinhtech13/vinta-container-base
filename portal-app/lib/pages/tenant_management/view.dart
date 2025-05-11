import 'package:collection/collection.dart';
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

  // Add scroll controllers
  final ScrollController _verticalScrollController = ScrollController();
  final ScrollController _bodyHorizontalController = ScrollController();
  final ScrollController _headerHorizontalController = ScrollController();

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
                  verticalScrollController: _verticalScrollController,
                  bodyHorizontalScrollController: _bodyHorizontalController,
                  headerHorizontalScrollController: _headerHorizontalController,
                  filter: controller.state.filter,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'Tenant ID', columnKey: 'tenantId', isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Title', columnKey: 'title'),
                    DataColumnSetting(index: 2, label: 'Status', columnKey: 'status'),
                    DataColumnSetting(index: 3, label: 'Description', columnKey: 'description'),
                    DataColumnSetting(index: 4, label: 'Domain URL', columnKey: 'domainHost'),
                    DataColumnSetting(index: 5, label: 'Created At', columnKey: 'createdAt'),
                  ],
                  dataRowBuilder: (tenant, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          switch (column.columnKey) {
                            case 'tenantId':
                              return DataCell(Text(tenant.id));
                            case 'title':
                              return DataCell(Text(tenant.title));
                            case 'status':
                              return DataCell(_buildStatusChip(tenant.status));
                            case 'description':
                              return DataCell(Text(tenant.description ?? '-'));
                            case 'domainHost':
                              return DataCell(Text(tenant.domainHost));
                            case 'createdAt':
                              return DataCell(Text(AppUtils.formatDateTime(tenant.createdAt)));
                            default:
                              return DataCell(Text('-'));
                          }
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
