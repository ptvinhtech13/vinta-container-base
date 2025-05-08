import 'package:flutter/material.dart';

import '../../commons/constants/colors.dart';
import '../app_layout/view.dart';
import 'controller.dart';

class HomePage extends AppPage<HomePageController> {
  HomePage({super.key}) {
    controller.hydrate();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Center(child: Text("Home Page", style: TextStyle(color: AppColors.colorPrimary01)));
  }
}
