import 'package:get/get.dart';

import '../../commons/routes/app_routes.dart';
import 'models/navigation_item_config.dart';
import 'state.dart';

class NavigationItemService extends GetxService {
  final state = NavigationItemState();

  Future<List<NavigationItem>> loadNavigationItem() {
    return Future.delayed(const Duration(seconds: 1), () {
      return <NavigationItem>[
        NavigationItem("Dashboard"),
        NavigationItem("Tenant Management"),
        NavigationItem(
          "Dashboard & Reports",
          children: [NavigationItem("Demo Metabase", route: AppRoutes.demoMetabase), NavigationItem("Dashboards"), NavigationItem("Reports")],
        ),
        NavigationItem("Settings"),
      ];
    }).then((configs) {
      state.userNavigationItems.clear();
      state.userNavigationItems.addAll(configs);
      return state.userNavigationItems;
    });
  }
}
