import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/vinta_loading/view.dart';

import '../../commons/constants/colors.dart';
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

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context);
    return Scaffold(
      resizeToAvoidBottomInset: true,
      backgroundColor: AppColors.colorPrimary11,
      body: Stack(
        children: [
          Row(
            children: [
              showNavigationSideBar ? SizedBox(width: 280, height: double.infinity, child: SideNavigationDrawer()) : const SizedBox.shrink(),
              Expanded(
                child: Stack(
                  children: [
                    Positioned(
                      top: 0,
                      left: 0,
                      right: 0,
                      height: 50,
                      child: showNavigationSideBar ? Container(color: Colors.amber, child: Text("data")) : const SizedBox.shrink(),
                    ),
                    Positioned(top: 50, left: 0, right: 0, bottom: 0, child: buildUI(context)),
                  ],
                ),
              ),
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
    );
  }

  Widget buildUI(BuildContext context);
}
