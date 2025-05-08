import 'package:flutter/material.dart';

import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class UserManagementPage extends AppPage<UserManagementPageController> {
  UserManagementPage({super.key}) {
    UserManagementPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("User Management Page"));
  }
}
