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
import '../../services/users/models.dart';
import '../../services/users/requests.dart';
import '../app_layout/view.dart';
import 'controller.dart';

class AccountPage extends AppPage<AccountPageController> {
  final ScrollController _scrollController = ScrollController();

  @override
  Widget buildUI(BuildContext context) {
    return ContentLayout(
      title: 'Tenant Management',
      breadcrumbPaths: [AppNavigationItemConfig.home, AppNavigationItemConfig.userManagementAccounts],
      content: [
        // _buildFilterPanel(),
        AppSpaces.spaceH16,
        Expanded(
          child: Card(
            elevation: 2,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(
                () => VintaPagingDataTable<UserModel, UserFilter>(
                  scrollController: _scrollController,
                  filter: controller.state.userFilter.value,
                  columnSettings: [
                    DataColumnSetting(index: 0, label: 'ID', columnKey: 'id', size: ColumnSize.M, isVisible: true, isSortable: true),
                    DataColumnSetting(index: 1, label: 'User Type', size: ColumnSize.M, columnKey: 'userType', isSortable: true),
                    DataColumnSetting(index: 2, label: 'Status', size: ColumnSize.S, columnKey: 'userStatus', isSortable: false),
                    DataColumnSetting(index: 3, label: 'Phone Number', size: ColumnSize.M, columnKey: 'phoneNumber', isSortable: false),
                    DataColumnSetting(index: 4, label: 'Email', size: ColumnSize.M, columnKey: 'email', isSortable: false),
                    DataColumnSetting(index: 5, label: 'Full Name', size: ColumnSize.M, columnKey: 'fullName', isSortable: false),
                    DataColumnSetting(index: 6, label: 'Roles', size: ColumnSize.M, columnKey: 'userRoles', isSortable: false),
                    DataColumnSetting(index: 5, size: ColumnSize.S, label: 'Created At', columnKey: 'createdAt', isSortable: true),
                  ],
                  dataRowBuilder: (user, columnSettings) {
                    final cells =
                        columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((column) {
                          final Widget child = switch (column.columnKey) {
                            'id' => SelectableText(user.id, style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold)),
                            'userType' => SelectableText(user.userType.name, style: TextStyle(fontSize: 14)),
                            'userStatus' => SelectableText(user.userStatus.name, style: TextStyle(fontSize: 14)),
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
}
