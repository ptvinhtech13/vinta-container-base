import 'package:containerbase/pages/app_layout/index.dart';
import 'package:flutter/material.dart';

import 'bindings.dart';
import 'controller.dart';

class GroupManagementPage extends AppPage<GroupManagementPageController> {
  GroupManagementPage({super.key}) {
    GroupManagementPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("Group Management Page"));
  }
}
