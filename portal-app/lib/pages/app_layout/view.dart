import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';

import '../../commons/constants/colors.dart';
import '../../commons/routes/app_routes.dart';
import '../../commons/widgets/side_navigation/view.dart';
import '../../generated/assets.gen.dart';
import 'controller.dart';

abstract class AppPage<T extends GetxController> extends GetView<T> {
  final bool showNavigationSideBar;
  final bool showAppBar;
  final Color? backgroundWhiteColor;

  late final AppPageController appPageController;

  AppPage({super.key, this.showNavigationSideBar = true, this.showAppBar = true, this.backgroundWhiteColor}) {
    appPageController = Get.put(AppPageController());
  }

  void toggleSideNav() {
    appPageController.state.isSideNavOpen.value = !appPageController.state.isSideNavOpen.value;
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context);
    return Scaffold(
      resizeToAvoidBottomInset: true,
      backgroundColor: AppColors.colorPrimary11,
      body: Column(
        children: [
          showAppBar ? _buildAppBar() : const SizedBox.shrink(),
          Expanded(
            child: Stack(
              children: [
                Row(
                  children: [
                    showNavigationSideBar
                        ? Obx(
                          () =>
                              appPageController.state.isSideNavOpen.value
                                  ? SizedBox(width: 280, child: SideNavigationDrawer())
                                  : const SizedBox.shrink(),
                        )
                        : const SizedBox.shrink(),
                    Expanded(child: buildUI(context)),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget buildUI(BuildContext context);

  Widget _buildAppBar() {
    return Column(
      children: [
        Container(
          height: 56, // Adjusted to maintain total height of 60 with the progress indicator
          color: AppColors.colorPrimary01,
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  // Toggle button for side navigation
                  if (showNavigationSideBar)
                    IconButton(icon: const Icon(Icons.menu, color: Colors.white), onPressed: toggleSideNav, tooltip: 'Toggle navigation'),
                  if (showNavigationSideBar) const SizedBox(width: 16),
                  TextButton(
                    onPressed: () => Get.offAllNamed(AppRoutes.home),
                    style: TextButton.styleFrom(padding: const EdgeInsets.symmetric(horizontal: 8)),
                    child: Row(
                      children: [
                        Assets.icons.appLogo.image(height: 30, width: 30),
                        const SizedBox(width: 8),
                        Obx(
                          () => Text(
                            appPageController.tenantService.state.currentTenant.value.title,
                            style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold),
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              Row(
                children: [
                  // Notification button
                  IconButton(
                    icon: const Icon(Icons.notifications_outlined, color: Colors.white),
                    onPressed: () {
                      // Handle notification button press
                    },
                    tooltip: 'Notifications',
                  ),
                  const SizedBox(width: 8),

                  PopupMenuButton<String>(
                    offset: const Offset(0, 56),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                    tooltip: 'User profile',
                    child: Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 8.0),
                      child: Row(
                        children: [
                          const CircleAvatar(backgroundColor: Colors.white, radius: 16, child: Icon(Icons.person, color: AppColors.colorPrimary01)),
                          const SizedBox(width: 8),
                          const Icon(Icons.arrow_drop_down, color: Colors.white),
                        ],
                      ),
                    ),
                    itemBuilder:
                        (context) => [
                          PopupMenuItem<String>(
                            enabled: false,
                            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                            height: 150,
                            child: SizedBox(
                              width: 250,
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisSize: MainAxisSize.min,
                                children: [
                                  Row(
                                    children: [
                                      CircleAvatar(
                                        backgroundColor: AppColors.colorPrimary01,
                                        radius: 24,
                                        child: Icon(Icons.person, color: Colors.white, size: 32),
                                      ),
                                      SizedBox(width: 12),
                                      Expanded(
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            SelectableText(
                                              appPageController.userAuthenticationService.state.currentUser.value?.fullName ?? '-',
                                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16, color: Colors.black54),
                                            ),
                                            Text(
                                              appPageController.userAuthenticationService.state.currentUser.value?.userRoles
                                                      .map((e) => e.roleModel.title)
                                                      .join(', ') ??
                                                  '-',
                                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14, color: Colors.black38),
                                            ),
                                            Text(
                                              "Logged: ${appPageController.tenantService.state.currentTenant.value.title}",
                                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 14, color: Colors.black38),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                  const SizedBox(height: 12),
                                  SelectableText(
                                    'AccountID: ${appPageController.userAuthenticationService.state.currentUser.value?.id ?? '-'}',
                                    style: TextStyle(fontSize: 14, color: Colors.grey.shade700),
                                  ),
                                  SelectableText(
                                    'Email: ${appPageController.userAuthenticationService.state.currentUser.value?.email ?? '-'}',
                                    style: TextStyle(fontSize: 14, color: Colors.grey.shade700),
                                  ),
                                  const SizedBox(height: 8),
                                  const Divider(),
                                ],
                              ),
                            ),
                          ),
                          // Logout option
                          PopupMenuItem<String>(
                            value: 'logout',
                            height: 40,
                            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 0),
                            child: Row(
                              children: [
                                const Icon(Icons.logout, color: AppColors.colorError03),
                                const SizedBox(width: 8),
                                const Text('Logout Tenant', style: TextStyle(color: AppColors.colorError03)),
                              ],
                            ),
                            onTap: () {
                              appPageController.tenantService.logoutTenant();
                            },
                          ),
                          PopupMenuItem<String>(
                            value: 'logout',
                            height: 40,
                            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 0),
                            child: Row(
                              children: [
                                const Icon(Icons.logout, color: AppColors.colorError03),
                                const SizedBox(width: 8),
                                const Text('Logout', style: TextStyle(color: AppColors.colorError03)),
                              ],
                            ),
                            onTap: () {
                              // Add a small delay to allow the popup to close before logging out
                              Future.delayed(const Duration(milliseconds: 100), () {
                                appPageController.userAuthenticationService.logout();
                              });
                            },
                          ),
                        ],
                  ),
                ],
              ),
            ],
          ),
        ),
        // Linear Progress Indicator at the top of AppBar
        Obx(
          () =>
              appPageController.state.isShowLoadingCounter.value > 0
                  ? LinearProgressIndicator(backgroundColor: AppColors.colorPrimary02, color: Colors.greenAccent, minHeight: 4)
                  : const SizedBox(height: 4),
        ),
      ],
    );
  }
}
