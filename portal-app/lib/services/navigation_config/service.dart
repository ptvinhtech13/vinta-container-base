import 'package:get/get.dart';

import 'models/navigation_item_config.dart';
import 'state.dart';

class NavigationItemConfigService extends GetxService {
  final state = NavigationItemConfigState();

  Future<List<NavigationItemConfig>> loadNavigationItemConfig() {
    return Future.delayed(const Duration(seconds: 2), () {
      return <NavigationItemConfig>[
        NavigationItemConfig("Dashboard", children: [NavigationItemConfig("Dashboard 1"), NavigationItemConfig("Dashboard 2")]),
        NavigationItemConfig("Reports", children: [NavigationItemConfig("Report 1"), NavigationItemConfig("Report 2")]),
        NavigationItemConfig("Settings", children: [NavigationItemConfig("Setting 1"), NavigationItemConfig("Setting 2")]),
      ];
    }).then((configs) {
      state.userNavigationItems.clear();
      state.userNavigationItems.addAll(configs);
      return state.userNavigationItems;
    });
  }
}
