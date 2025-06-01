import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';

import '../../constants/colors.dart' show AppColors;
import '../dialog_modal/app_dialog_view.dart';
import 'bindings.dart';
import 'controller.dart';

class AddRolePermissionModal extends AppDialogView<AddRolePermissionModalController> {
  final _formKey = GlobalKey<FormState>();

  AddRolePermissionModal({super.key}) {
    AddRolePermissionModalBindings().dependencies();
  }

  @override
  double get height => 0.8.sh;

  @override
  double get width => 0.8.sw;

  @override
  String get title => "Add New Role";

  @override
  Widget buildDialogContent(BuildContext context) {
    return Form(
      key: _formKey,
      child: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        physics: const AlwaysScrollableScrollPhysics(),
        child: Column(
          children: [
            AppSpaces.spaceH16,
            TextFormField(
              decoration: InputDecoration(
                labelText: 'Role Title *',
                border: OutlineInputBorder(),
                contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 16),
              ),
              validator: (value) {
                if (value == null || value.trim().isEmpty) {
                  return 'Role title is required';
                }
                return null;
              },
            ),

          ],
        ),
      ),
    );
  }

  @override
  Widget buildActions(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        TextButton(onPressed: () => Navigator.of(context).pop(), child: Text('Cancel')),
        AppSpaces.spaceW16,
        ElevatedButton(
          onPressed: () {
            if (!_formKey.currentState!.validate()) {
              return;
            }
            Navigator.of(context).pop();
          },
          style: ElevatedButton.styleFrom(
            backgroundColor: AppColors.colorPrimary01,
            foregroundColor: Colors.white,
            padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
          ),
          child: Text('Save'),
        ),
      ],
    );
  }
}
