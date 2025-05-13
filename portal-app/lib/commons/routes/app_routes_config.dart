import 'package:containerbase/pages/role_permissions/index.dart';
import 'package:containerbase/pages/tenant_management/index.dart';
import 'package:get/get.dart';

import '../../pages/accounts/bindings.dart';
import '../../pages/accounts/view.dart';
import '../../pages/demo_metabase/index.dart';
import '../../pages/group_management/index.dart';
import '../../pages/home/bindings.dart';
import '../../pages/home/view.dart';
import '../../pages/welcome/index.dart';
import '../middlewares/index.dart';
import 'app_routes.dart';

class AppRoutingConfigurations {
  static final List<GetPage> routes = [
    GetPage(
      name: AppRoutes.welcome,
      page: () => WelcomePage(),
      binding: WelcomePageBinding(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.home,
      page: () => HomePage(),
      binding: HomePageBinding(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.demoMetabase,
      page: () => DemoMetabasePage(),
      binding: DemoMetabasePageBinding(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.rolePermissions,
      page: () => RolePermissionPage(),
      binding: RolePermissionPageBindings(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.tenantManagement,
      page: () => TenantManagementPage(),
      binding: TenantManagementPageBindings(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.userManagementUsers,
      page: () => AccountPage(),
      binding: AccountPageBindings(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
    GetPage(
      name: AppRoutes.userManagementGroups,
      page: () => GroupManagementPage(),
      binding: GroupManagementPageBindings(),
      middlewares: [UserAccessCheckerMiddleware(priority: 1)],
      transition: Transition.noTransition,
    ),
  ];
}
