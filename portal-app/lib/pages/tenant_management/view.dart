import 'package:flutter/material.dart';

import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class TenantManagementPage extends AppPage<TenantManagementPageController> {
  TenantManagementPage({super.key}) {
    TenantManagementPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("Tenant Management Page"));
  }
}
