import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../commons/routes/app_routes.dart';
import 'models/navigation_item.dart';
import 'state.dart';

class NavigationItemService extends GetxService {
  final state = NavigationItemState();

  // https://fonts.google.com/icons?selected=Material+Icons:dashboard:&icon.query=dashboard&icon.size=24&icon.color=%231f1f1f
  Future<List<NavigationItem>> loadNavigationItem() {
    return Future.delayed(const Duration(seconds: 1), () {
      return <NavigationItem>[
        NavigationItem("Home", displayOrder: 0, route: AppRoutes.home, iconCode: Icons.home.codePoint),
        NavigationItem("Tenant Management", displayOrder: 1, route: AppRoutes.tenantManagement, iconCode: Icons.business.codePoint),
        NavigationItem(
          "User Management",
          displayOrder: 2,
          route: AppRoutes.userManagement,
          iconCode: Icons.contacts.codePoint,
          children: [
            NavigationItem("Users", displayOrder: 1, route: AppRoutes.userManagementUsers, iconCode: Icons.supervisor_account.codePoint),
            NavigationItem("Groups", displayOrder: 2, route: AppRoutes.userManagementGroups, iconCode: Icons.groups.codePoint),
          ],
        ),
        NavigationItem("Role & Permissions", displayOrder: 3, route: AppRoutes.rolePermissions, iconCode: Icons.security.codePoint),
        NavigationItem("Quotations"),
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
