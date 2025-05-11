import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/vinta_loading/view.dart';

import '../../commons/constants/colors.dart';
import '../../commons/widgets/side_navigation/view.dart';
import '../../generated/assets.gen.dart';
import '../../services/user_authentication/service.dart';
import 'controller.dart';

abstract class AppPage<T extends GetxController> extends GetView<T> {
  final bool showNavigationSideBar;
  final bool showAppBar;
  final Color? backgroundWhiteColor;

  late final AppPageController appPageController;
  final isSideNavOpen = true.obs;

  AppPage({super.key, this.showNavigationSideBar = true, this.showAppBar = true, this.backgroundWhiteColor}) {
    appPageController = Get.put(AppPageController());
  }

  void toggleSideNav() {
    isSideNavOpen.value = !isSideNavOpen.value;
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
                          () => AnimatedContainer(
                            duration: const Duration(milliseconds: 300),
                            width: isSideNavOpen.value ? 280 : 0,
                            height: double.infinity,
                            child: isSideNavOpen.value ? SideNavigationDrawer() : const SizedBox.shrink(),
                          ),
                        )
                        : const SizedBox.shrink(),
                    Expanded(child: buildUI(context)),
                  ],
                ),
                Obx(() {
                  return appPageController.state.isShowLoadingCounter.value > 0
                      ? Visibility(
                        visible: true,
                        child: Container(
                          decoration: BoxDecoration(
                            color: backgroundWhiteColor ?? Colors.white54,
                            borderRadius: const BorderRadius.all(Radius.circular(0)),
                          ),
                          child: Center(
                            child: VintaLoadingProgress(
                              height: 130,
                              width: 130,
                              loadingWidget: Assets.icons.lottieShipContainer.lottie(height: 130, width: 130, repeat: true),
                            ),
                          ),
                        ),
                      )
                      : const SizedBox.shrink();
                }),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget buildUI(BuildContext context);

  Widget _buildAppBar() {
    return Container(
      height: 60,
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
              // ContainerBase logo
              TextButton(
                onPressed: () => Get.offAllNamed('/home'),
                style: TextButton.styleFrom(padding: const EdgeInsets.symmetric(horizontal: 8)),
                child: Row(
                  children: [
                    Assets.icons.appLogo.image(height: 30, width: 30),
                    const SizedBox(width: 8),
                    const Text('ContainerBase', style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold)),
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
              // User profile avatar with dropdown
              _buildUserProfileDropdown(),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildUserProfileDropdown() {
    final userAuthService = Get.find<UserAuthenticationService>();

    return PopupMenuButton<String>(
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
            // User info header
            PopupMenuItem<String>(
              enabled: false,
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
              height: 150,
              child: SizedBox(
                width: 220,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Row(
                      children: [
                        CircleAvatar(backgroundColor: AppColors.colorPrimary01, radius: 24, child: Icon(Icons.person, color: Colors.white, size: 32)),
                        SizedBox(width: 12),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('John Doe', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                              Text('Administrator', style: TextStyle(color: Colors.grey)),
                            ],
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    const Text('Account ID: ACC123456', style: TextStyle(fontSize: 12, color: Colors.grey)),
                    const Text('john.doe@example.com', style: TextStyle(fontSize: 12, color: Colors.grey)),
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
                  const Text('Logout', style: TextStyle(color: AppColors.colorError03)),
                ],
              ),
              onTap: () {
                // Add a small delay to allow the popup to close before logging out
                Future.delayed(const Duration(milliseconds: 100), () {
                  userAuthService.logout();
                });
              },
            ),
          ],
    );
  }
}
