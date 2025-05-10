import 'package:get/get.dart';

import 'constants.dart';
import 'models/navigation_item.dart';
import 'state.dart';

class NavigationItemService extends GetxService {
  final state = NavigationItemState();

  // https://fonts.google.com/icons?selected=Material+Icons:dashboard:&icon.query=dashboard&icon.size=24&icon.color=%231f1f1f
  Future<List<NavigationItem>> loadNavigationItem() {
    return Future.delayed(const Duration(seconds: 1), () {
      return <NavigationItem>[
        AppNavigationItemConfig.home,
        AppNavigationItemConfig.tenantManagement,
        AppNavigationItemConfig.rolePermissions,
        AppNavigationItemConfig.userManagementGroups,
        AppNavigationItemConfig.quotations,
        AppNavigationItemConfig.dashboardReports,
        AppNavigationItemConfig.settings,
      ];
    }).then((configs) {
      state.userNavigationItems.clear();
      state.userNavigationItems.addAll(configs);
      return state.userNavigationItems;
    });
  }
}
