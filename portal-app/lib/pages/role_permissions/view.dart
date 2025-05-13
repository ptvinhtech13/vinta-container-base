import 'package:collection/collection.dart';
import 'package:containerbase/pages/app_layout/index.dart';
import 'package:containerbase/services/roles/index.dart';
import 'package:containerbase/services/users/models.dart';
import 'package:data_table_2/data_table_2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/constants/index.dart';

import '../../commons/widgets/content_layout/view.dart';
import '../../commons/widgets/vinta_paging_datatable/models.dart';
import '../../commons/widgets/vinta_paging_datatable/view.dart';
import '../../services/navigation/constants.dart';
import 'controller.dart';

class RolePermissionPage extends AppPage<RolePermissionPageController> {
  final ScrollController _scrollController = ScrollController();

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Roles & Permissions',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.rolePermissions],
      content: [
        // _buildFilterPanel(),
        AppSpaces.spaceH16,
        Expanded(
          child: Card(
            elevation: 2,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(
                () => VintaPagingDataTable<UserRoleModel, UserRoleFilter>(
                  scrollController: _scrollController,
                  filter: controller.state.userRoleFilter.value,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'ID', columnKey: 'id', size: ColumnSize.M, isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Role', size: ColumnSize.M, columnKey: 'roleTitle', isSortable: true),
                    DataColumnSetting(index: 2, label: 'Description', size: ColumnSize.M, columnKey: 'description', isSortable: true),
                  ],
                  dataRowBuilder: (userRole, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          final Widget child = switch (column.columnKey) {
                            'id' => SelectableText(userRole.roleModel.id, style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold)),
                            'roleTitle' => SelectableText(userRole.roleModel.title, style: TextStyle(fontSize: 14)),
                            'description' => SelectableText(userRole.roleModel.description ?? '-', style: TextStyle(fontSize: 14)),
                            _ => SelectableText('-'),
                          };
                          return DataCell(child);
                        }).toList();

                    return DataRow(cells: cells);
                  },
                  dataLoader: (pageRequest) => controller.queryUserRoles(pageRequest),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
