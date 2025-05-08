import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/vinta_application_license/view.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';

import '../../../generated/assets.gen.dart';
import '../../../services/navigation/models/navigation_item.dart';
import '../../../services/navigation/service.dart';
import '../../constants/index.dart';

class SideNavigationDrawer extends GetView {
  final VoidCallback? logoutCallBack;
  final navigationItemConfigService = Get.find<NavigationItemService>();

  SideNavigationDrawer({super.key, this.logoutCallBack});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppColors.colorPrimary11,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          _buildDrawerHeader(),
          Expanded(
            child: Obx(
              () => ListView.builder(
                itemCount: navigationItemConfigService.state.userNavigationItems.length,
                shrinkWrap: true,
                physics: const AlwaysScrollableScrollPhysics(),
                padding: const EdgeInsets.only(bottom: 5),
                cacheExtent: 100000,
                itemBuilder: (context, index) {
                  return SideNavigationItemView(navItemConfig: navigationItemConfigService.state.userNavigationItems[index]);
                },
              ),
            ),
          ),
          AppLicenseBanner(),
          AppSpaces.spaceH20,
        ],
      ),
    );
  }

  Widget _buildDrawerHeader() {
    return DrawerHeader(
      padding: const EdgeInsets.fromLTRB(16.0, 16.0, 16.0, 8.0),
      decoration: const BoxDecoration(color: AppColors.colorPrimary01),
      child: Center(child: Assets.icons.progressLoading.lottie(height: 70, width: 70, repeat: true)),
    );
  }
}

class SideNavigationItemView extends GetView {
  final double? borderRadius;
  late final bool isSingle;
  final bool isChild;
  final NavigationItem navItemConfig;
  final isExpanded = false.obs;
  final VoidCallback? logoutCallBack;

  SideNavigationItemView({super.key, this.borderRadius, required this.navItemConfig, this.isChild = false, this.logoutCallBack}) {
    isSingle = navItemConfig.children.isEmpty;
  }

  double angleToRad(double angle) {
    return angle * 0.0174533;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Material(
          color: AppColors.colorPrimary11,
          borderRadius: borderRadius != null ? BorderRadius.circular(borderRadius!) : null,
          child: InkWell(
            borderRadius: borderRadius != null ? BorderRadius.circular(borderRadius!) : null,
            splashColor: AppColors.colorPrimary02,
            onTap:
                isSingle
                    ? () async {
                      await Future.delayed(const Duration(milliseconds: 150));
                      Get.offNamed(navItemConfig.route!);
                    }
                    : () {
                      isExpanded.value = !isExpanded.value;
                    },
            onLongPress: () {},
            child: Container(
              height: 50,
              decoration: borderRadius != null ? BoxDecoration(borderRadius: BorderRadius.circular(borderRadius!)) : null,
              padding: isChild ? const EdgeInsets.only(left: 20) : const EdgeInsets.only(left: 10),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Padding(padding: const EdgeInsets.only(right: 20), child: Icon(IconData(navItemConfig.iconCode!, fontFamily: "MaterialIcons"))),
                  Expanded(
                    child: Text(
                      navItemConfig.title.tr,
                      style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500, color: AppColors.colorPrimary06B),
                    ),
                  ),
                  if (!isSingle)
                    Obx(
                      () => Transform.rotate(
                        angle: isExpanded.isTrue ? angleToRad(90) : 0,
                        child: const Padding(padding: EdgeInsets.only(right: 5), child: Icon(Icons.chevron_right)),
                      ),
                    ),
                ],
              ),
            ),
          ),
        ),
        SizedBox(height: 2, child: Container(color: Colors.grey.shade300)),
        //* Content
        Obx(
          () => AnimatedSize(
            duration: const Duration(milliseconds: 200),
            curve: Curves.ease,
            child: Container(
              child:
                  isExpanded.isFalse
                      ? null
                      : Container(
                        decoration: BoxDecoration(borderRadius: BorderRadius.circular(20)),
                        width: double.infinity,
                        child: Column(
                          children:
                              !isSingle ? navItemConfig.children.map((e) => SideNavigationItemView(isChild: true, navItemConfig: e)).toList() : [],
                        ),
                      ),
            ),
          ),
        ),
      ],
    );
  }
}

class ForceRefreshRoutingPageEvent {}
