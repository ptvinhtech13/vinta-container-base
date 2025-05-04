import 'package:flutter/material.dart';

import '../../constants/colors.dart';

class AppFooter extends StatelessWidget {
  const AppFooter({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 24),
      color: AppColors.colorPrimary11,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Row(
            children: [
              TextButton(onPressed: () {}, child: Text('Privacy Policy')),
              TextButton(onPressed: () {}, child: Text('Terms of Service')),
              TextButton(onPressed: () {}, child: Text('Contact Us')),
            ],
          ),
        ],
      ),
    );
  }
}
