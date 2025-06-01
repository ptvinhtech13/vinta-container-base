import 'package:collection/collection.dart';
import 'package:data_table_2/data_table_2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/notifications/app_notification_service.dart';
import 'package:vinta_shared_commons/notifications/content_models.dart';
import 'package:vinta_shared_commons/notifications/models.dart' show AppNotificationHeader, AppNotificationMessage;
import 'package:vinta_shared_commons/utils/app_utils.dart';

import '../../commons/constants/colors.dart';
import '../../commons/widgets/content_layout/view.dart';
import '../../commons/widgets/vinta_paging_datatable/controller.dart';
import '../../commons/widgets/vinta_paging_datatable/models.dart';
import '../../commons/widgets/vinta_paging_datatable/view.dart';
import '../../services/navigation/constants.dart';
import '../../services/users/models.dart';
import '../../services/users/requests.dart';
import '../app_layout/view.dart';
import 'controller.dart';

class AccountPage extends AppPage<AccountPageController> {
  ScrollController get _scrollController => ScrollController();
  late final VintaPagingDataTableController<UserModel, UserFilter> _tableController;

  AccountPage({super.key}) {
    controller.hydrate();
    _tableController = Get.put(VintaPagingDataTableController<UserModel, UserFilter>(), tag: 'account-table');
  }

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Accounts',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.userManagementAccounts],
      actions: [
        ElevatedButton.icon(
          onPressed:
              () => controller.showAddUserModal().then((value) {
                if (value != null) {
                  Get.find<AppNotificationCenterManager>().sendNotificationMessage(
                    AppNotificationMessage(
                      header: AppNotificationHeader(),
                      content: AppNotifySuccessContent(successMessage: 'Added new user successfully'),
                    ),
                  );
                  _tableController.refreshTable();
                }
              }),
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
                () => VintaPagingDataTable<UserModel, UserFilter>(
                  scrollController: _scrollController,
                  filter: controller.state.userFilter.value,
                  controller: _tableController,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'ID', columnKey: 'id', size: ColumnSize.M, isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'Full Name', size: ColumnSize.M, columnKey: 'fullName', isSortable: true),
                    DataColumnSetting(index: 2, label: 'Status', size: ColumnSize.S, columnKey: 'userStatus', isSortable: true),
                    DataColumnSetting(index: 3, label: 'Email', size: ColumnSize.M, columnKey: 'email', isSortable: true),
                    DataColumnSetting(index: 4, label: 'Role', size: ColumnSize.M, columnKey: 'userRoles', isSortable: false),
                    DataColumnSetting(index: 5, label: 'Phone Number', size: ColumnSize.M, columnKey: 'phoneNumber', isSortable: false),
                    DataColumnSetting(index: 6, label: 'Created', size: ColumnSize.S, columnKey: 'createdAt', isSortable: true),
                    DataColumnSetting(index: 7, label: 'Actions', size: ColumnSize.S, columnKey: 'actions', isSortable: false),
                  ],
                  dataRowBuilder: (user, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          final Widget child = switch (column.columnKey) {
                            'id' => SelectableText(user.id, style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold)),
                            'userType' => SelectableText(user.userType.name, style: TextStyle(fontSize: 14)),
                            'userStatus' => _buildStatusChip(user.userStatus),
                            'phoneNumber' => SelectableText(user.phoneNumber ?? '-', style: TextStyle(fontSize: 14)),
                            'email' => SelectableText(user.email, style: TextStyle(fontSize: 14)),
                            'fullName' => SelectableText(user.fullName, style: TextStyle(fontSize: 14)),
                            'userRoles' => SelectableText(
                              user.userRoles.map((role) => role.roleModel.title).join(', '),
                              style: TextStyle(fontSize: 14),
                            ),
                            'createdAt' => SelectableText(
                              AppUtils.formatDateTime(user.createdAt, isAlreadyLocal: false),
                              style: TextStyle(fontSize: 14),
                            ),
                            'actions' => Row(
                              children: [
                                PopupMenuButton<String>(
                                  icon: Icon(Icons.more_vert, color: AppColors.colorPrimary01),
                                  onSelected: (value) {
                                    switch (value) {
                                      case 'view':
                                        // Handle view action
                                        break;
                                      case 'resend':
                                        // Handle resend action
                                        break;
                                      case 'block':
                                        // Handle block action
                                        break;
                                    }
                                  },
                                  itemBuilder:
                                      (context) => [
                                        PopupMenuItem(value: 'view', child: Row(children: [Text('View Profile', style: TextStyle(fontSize: 14))])),
                                        PopupMenuItem(
                                          value: 'resend',
                                          child: Row(children: [Text('Reset Password', style: TextStyle(fontSize: 14))]),
                                        ),
                                        PopupMenuItem(
                                          value: 'block',
                                          child: Row(children: [Text('Block', style: TextStyle(color: Colors.red, fontSize: 14))]),
                                        ),
                                      ],
                                ),
                              ],
                            ),
                            _ => SelectableText('-'),
                          };
                          return DataCell(child);
                        }).toList();

                    return DataRow(cells: cells);
                  },
                  dataLoader: (pageRequest) => controller.queryUsers(pageRequest),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildStatusChip(UserStatus userStatus) {
    Color chipColor;
    switch (userStatus) {
      case UserStatus.CREATED:
        chipColor = Colors.grey;
        break;
      case UserStatus.ACTIVE:
        chipColor = AppColors.colorPrimary01;
        break;
      case UserStatus.ARCHIVED:
        chipColor = Colors.orangeAccent.shade400;
        break;
      case UserStatus.DELETING:
        chipColor = Colors.redAccent;
        break;
      case UserStatus.BLOCKED:
        chipColor = Colors.red;
        break;
    }

    return Chip(
      label: Text(userStatus.name, style: TextStyle(color: Colors.white, fontSize: 14)),
      backgroundColor: chipColor,
      padding: EdgeInsets.symmetric(horizontal: 4),
      labelPadding: EdgeInsets.symmetric(horizontal: 4),
    );
  }
}
