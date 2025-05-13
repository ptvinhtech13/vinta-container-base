import 'package:flutter/material.dart';

import '../../commons/routes/app_routes.dart';
import 'models/navigation_item.dart';

class AppNavigationItemConfig {
  static final NavigationItem home = NavigationItem("Home", displayOrder: 0, route: AppRoutes.home, iconCode: Icons.home.codePoint);
  static final NavigationItem tenantManagement = NavigationItem(
    "Tenant Management",
    displayOrder: 1,
    route: AppRoutes.tenantManagement,
    iconCode: Icons.business.codePoint,
  );

  static final NavigationItem userManagementAccounts = NavigationItem(
    "Accounts",
    displayOrder: 1,
    route: AppRoutes.userManagementUsers,
    iconCode: Icons.supervisor_account.codePoint,
  );

  static final NavigationItem rolePermissions = NavigationItem(
    "Role & Permissions",
    displayOrder: 2,
    route: AppRoutes.rolePermissions,
    iconCode: Icons.security.codePoint,
  );

  static final NavigationItem userManagementGroups = NavigationItem(
    "Groups",
    displayOrder: 3,
    route: AppRoutes.userManagementGroups,
    iconCode: Icons.groups.codePoint,
  );

  static final NavigationItem userManagement = NavigationItem(
    "User Management",
    displayOrder: 2,
    iconCode: Icons.contacts.codePoint,
    children: [userManagementAccounts, rolePermissions, userManagementGroups],
  );

  static final NavigationItem quotations = NavigationItem("Quotations", displayOrder: 3);

  static final NavigationItem dashboardReportsDemoMetabase = NavigationItem("Demo Metabase", route: AppRoutes.demoMetabase);
  static final NavigationItem dashboardReportsDashboards = NavigationItem("Dashboards");
  static final NavigationItem dashboardReportsReport = NavigationItem("Reports");

  static final NavigationItem dashboardReports = NavigationItem(
    "Dashboard & Reports",
    displayOrder: 4,
    iconCode: Icons.dashboard.codePoint,
    children: [dashboardReportsDemoMetabase, dashboardReportsDashboards, dashboardReportsReport],
  );
  static final settings = NavigationItem("Settings");
}
