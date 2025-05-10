import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../services/navigation/models/navigation_item.dart';
import '../../constants/colors.dart';

class AppBreadcrumbs extends StatelessWidget {
  final List<NavigationItem> routePaths;

  const AppBreadcrumbs({super.key, this.routePaths = const []});

  @override
  Widget build(BuildContext context) {
    return routePaths.isEmpty
        ? const SizedBox.shrink()
        : Padding(
          padding: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
          child: Wrap(
            alignment: WrapAlignment.start,
            crossAxisAlignment: WrapCrossAlignment.center,
            children: [
              ...List.generate(routePaths.length - 1, (index) {
                return [
                  InkWell(
                    onTap: () => Get.offAndToNamed(routePaths[index].route!),
                    child: Text(routePaths[index].title, style: TextStyle(color: AppColors.colorPrimary01, fontWeight: FontWeight.w500)),
                  ),
                  Padding(padding: EdgeInsets.symmetric(horizontal: 8.0), child: Icon(Icons.chevron_right, size: 16, color: Colors.grey)),
                ];
              }).expand((element) => element),
              Text(routePaths[routePaths.length - 1].title, style: TextStyle(color: Colors.grey, fontWeight: FontWeight.w500)),
            ],
          ),
        );
  }
}
