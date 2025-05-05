import 'package:containerbase/pages/home_page/index.dart';
import 'package:get/get.dart';

import '../../pages/demo_metabase/index.dart';
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
  ];
}
