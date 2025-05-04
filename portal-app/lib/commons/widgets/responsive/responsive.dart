import 'package:flutter/material.dart';

class Responsive extends StatelessWidget {
  final Widget mobile;
  final Widget desktop;

  const Responsive({super.key, required this.mobile, required this.desktop});

  // This isMobile, isTablet, isDesktop help us later
  static bool isMobile(BuildContext context) => MediaQuery.of(context).size.width < 1024;

  static bool isDesktop(BuildContext context) => MediaQuery.of(context).size.width >= 1024;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      // If our width is more than 1024 then we consider it a desktop
      builder: (context, constraints) {
        if (constraints.maxWidth >= 1024) {
          return desktop;
        }
        // If width it less then 1024 and more then 640 we consider it as tablet
        // else if (constraints.maxWidth >= 640) {
        //   return tablet;
        // }
        // Or less then that we called it mobile
        else {
          return mobile;
        }
      },
    );
  }
}
