import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';

import '../../constants/colors.dart';

abstract class AppDialogView<T extends GetxController> extends GetView<T> {
  const AppDialogView({super.key});

  Widget buildDialogContent(BuildContext context);

  Widget buildActions(BuildContext context);

  double get width;

  double get height;

  String get title;

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context);
    return Dialog(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Container(
        width: width,
        height: height,
        padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 24),
        child: Stack(
          children: [
            Align(
              alignment: Alignment.topLeft,
              child: SizedBox(
                height: 56,
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(title, style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600, color: AppColors.colorPrimary01)),
                        IconButton(onPressed: () => Navigator.of(context).pop(), icon: Icon(Icons.close)),
                      ],
                    ),
                    Divider(),
                  ],
                ),
              ),
            ),
            Positioned(top: 56, left: 0, right: 0, bottom: 50, child: buildDialogContent(context)),
            Align(alignment: Alignment.bottomRight, child: SizedBox(height: 50, child: buildActions(context))),
          ],
        ),
      ),
    );
  }
}
