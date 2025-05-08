import 'package:flutter/material.dart';

import '../app_layout/view.dart';
import 'bindings.dart';
import 'controller.dart';

class NotFoundPage extends AppPage<NotFoundPageController> {
  NotFoundPage({super.key}) {
    NotFoundPageBindings().dependencies();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("Not Found Page"));
  }
}
