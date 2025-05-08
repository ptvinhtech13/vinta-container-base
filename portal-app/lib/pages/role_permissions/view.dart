import 'package:containerbase/pages/app_layout/index.dart';
import 'package:flutter/material.dart';

import 'bindings.dart';
import 'controller.dart';

class RolePermissionPage extends AppPage<RolePermissionPageController> {
  RolePermissionPage({super.key}) {
    RolePermissionPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("Role & Permissions Page"));
  }
}
