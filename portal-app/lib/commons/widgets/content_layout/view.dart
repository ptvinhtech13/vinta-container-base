import 'package:flutter/material.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';

import '../../../services/navigation/models/navigation_item.dart';
import '../../constants/colors.dart';
import '../breadcrumbs/view.dart';

class ContentLayout extends StatelessWidget {
  final List<Widget> content;
  final List<NavigationItem> breadcrumbPaths;
  final String title;
  final List<Widget>? actions;

  const ContentLayout({super.key, required this.content, required this.title, this.breadcrumbPaths = const [], this.actions});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(title, style: TextStyle(fontSize: 24, fontWeight: FontWeight.w600, color: AppColors.colorPrimary01)),
          AppSpaces.spaceH16,
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [AppBreadcrumbs(routePaths: breadcrumbPaths), if (actions != null) Row(children: actions!)],
          ),
          AppSpaces.spaceH16,
          ...content,
        ],
      ),
    );
  }
}
