import 'package:collection/collection.dart';
import 'package:containerbase/pages/app_layout/index.dart';
import 'package:containerbase/services/roles/index.dart';
import 'package:containerbase/services/users/models.dart';
import 'package:data_table_2/data_table_2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/notifications/app_notification_service.dart';
import 'package:vinta_shared_commons/notifications/content_models.dart';
import 'package:vinta_shared_commons/notifications/models.dart';

import '../../commons/constants/colors.dart';
import '../../commons/widgets/content_layout/view.dart';
import '../../commons/widgets/vinta_paging_datatable/controller.dart';
import '../../commons/widgets/vinta_paging_datatable/models.dart';
import '../../commons/widgets/vinta_paging_datatable/view.dart';
import '../../services/navigation/constants.dart';
import 'controller.dart';

class RolePermissionPage extends AppPage<RolePermissionPageController> {
  final ScrollController _scrollController = ScrollController();
  late final VintaPagingDataTableController<UserRoleModel, UserRoleFilter> _tableController;

  RolePermissionPage({super.key}) {
    _tableController = Get.put(VintaPagingDataTableController<UserRoleModel, UserRoleFilter>(), tag: 'role-permission-table');
  }

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Roles & Permissions',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.rolePermissions],
      actions: [
        ElevatedButton.icon(
          onPressed: () => controller.showAddRoleModal()
              .then((value) {
            if (value != null) {
              Get.find<AppNotificationCenterManager>().sendNotificationMessage(
                AppNotificationMessage(
                  header: AppNotificationHeader(),
                  content: AppNotifySuccessContent(successMessage: 'Added new role successfully'),
                ),
              );
              _tableController.refreshTable();
            }
          })
          ,
          icon: Icon(Icons.add, color: Colors.white),
          label: Text('Add', style: TextStyle(color: Colors.white)),
          style: ElevatedButton.styleFrom(backgroundColor: AppColors.colorPrimary01, padding: EdgeInsets.symmetric(horizontal: 16, vertical: 12)),
        ),
      ],
      content: [
        Expanded(
          child: Card(
            elevation: 2,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(
                () => VintaPagingDataTable<UserRoleModel, UserRoleFilter>(
                  scrollController: _scrollController,
                  filter: controller.state.userRoleFilter.value,
                  controller: _tableController,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'ID', columnKey: 'id', size: ColumnSize.M, isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Role', size: ColumnSize.M, columnKey: 'roleTitle', isSortable: true),
                    DataColumnSetting(index: 2, label: 'Description', size: ColumnSize.M, columnKey: 'description', isSortable: false),
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
