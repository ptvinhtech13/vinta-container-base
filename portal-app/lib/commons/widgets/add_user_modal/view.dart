import 'dart:math';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';
import 'package:vinta_shared_commons/notifications/app_notification_service.dart';
import 'package:vinta_shared_commons/notifications/content_models.dart';
import 'package:vinta_shared_commons/notifications/models.dart';

import '../../../services/clients/rest/apis/users/dtos.dart';
import '../../../services/roles/models.dart';
import '../../../services/users/models.dart';
import '../../constants/colors.dart';

class AddUserModal extends StatefulWidget {
  final Future<UserModel> Function(CreateUserRequest) onSave;
  final List<RoleModel> availableRoles;
  final String tenantId;

  const AddUserModal({super.key, required this.onSave, required this.availableRoles, required this.tenantId});

  @override
  State<AddUserModal> createState() => _AddUserModalState();
}

class _AddUserModalState extends State<AddUserModal> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _phoneController = TextEditingController();

  String? _selectedRoleId;
  bool _isLoading = false;

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _phoneController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Container(
        width: 500,
        padding: const EdgeInsets.all(24),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text('Add New User', style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600, color: AppColors.colorPrimary01)),
                  IconButton(onPressed: () => Navigator.of(context).pop(), icon: Icon(Icons.close)),
                ],
              ),
              AppSpaces.spaceH24,

              // Full Name Field
              TextFormField(
                controller: _nameController,
                decoration: InputDecoration(
                  labelText: 'Full Name *',
                  border: OutlineInputBorder(),
                  contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 16),
                ),
                validator: (value) {
                  if (value == null || value.trim().isEmpty) {
                    return 'Full name is required';
                  }
                  return null;
                },
              ),
              AppSpaces.spaceH16,

              // Email Field
              TextFormField(
                controller: _emailController,
                decoration: InputDecoration(
                  labelText: 'Email *',
                  border: OutlineInputBorder(),
                  contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 16),
                ),
                validator: (value) {
                  if (value == null || value.trim().isEmpty) {
                    return 'Email is required';
                  }
                  if (!GetUtils.isEmail(value.trim())) {
                    return 'Please enter a valid email';
                  }
                  return null;
                },
              ),
              AppSpaces.spaceH16,

              // Phone Number Field
              TextFormField(
                controller: _phoneController,
                decoration: InputDecoration(
                  labelText: 'Phone Number',
                  border: OutlineInputBorder(),
                  contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 16),
                ),
              ),
              AppSpaces.spaceH16,

              // User Role Dropdown
              DropdownButtonFormField<String>(
                value: _selectedRoleId,
                decoration: InputDecoration(
                  labelText: 'User Role *',
                  border: OutlineInputBorder(),
                  contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 16),
                ),
                items:
                    widget.availableRoles.map((role) {
                      return DropdownMenuItem<String>(
                        value: role.id,
                        child: Column(crossAxisAlignment: CrossAxisAlignment.start, mainAxisSize: MainAxisSize.min, children: [Text(role.title)]),
                      );
                    }).toList(),
                onChanged: (value) {
                  setState(() {
                    _selectedRoleId = value;
                  });
                },
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please select a role';
                  }
                  return null;
                },
              ),
              AppSpaces.spaceH24,

              // Action Buttons
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  TextButton(onPressed: _isLoading ? null : () => Navigator.of(context).pop(), child: Text('Cancel')),
                  AppSpaces.spaceW16,
                  ElevatedButton(
                    onPressed: _isLoading ? null : _handleSave,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.colorPrimary01,
                      foregroundColor: Colors.white,
                      padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                    ),
                    child:
                        _isLoading
                            ? SizedBox(
                              width: 16,
                              height: 16,
                              child: CircularProgressIndicator(strokeWidth: 2, valueColor: AlwaysStoppedAnimation<Color>(Colors.white)),
                            )
                            : Text('Save'),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _handleSave() {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    if (_selectedRoleId == null) {
      Get.snackbar('Validation Error', 'Please select a role', backgroundColor: Colors.red.shade100, colorText: Colors.red.shade800);
      return;
    }

    setState(() {
      _isLoading = true;
    });

    final request = CreateUserRequest(
      email: _emailController.text.trim(),
      fullName: _nameController.text.trim(),
      phoneNumber: _phoneController.text.trim().isEmpty ? null : _phoneController.text.trim(),
      userType: UserType.BACK_OFFICE,
      roleId: _selectedRoleId!,
      userAccess: CreateUserBasicAuthRequest(password: generateSecurePassword()),
    );
    widget
        .onSave(request)
        .then((user) {
          Get.back(result: user);
        })
        .catchError((error) {
          setState(() {
            _isLoading = false;
          });
          Get.find<AppNotificationCenterManager>().sendNotificationMessage(
            AppNotificationMessage(header: AppNotificationHeader(), content: AppNotifyErrorContent(errorCode: -1, errorMessage: e.toString())),
          );
        });
  }

  String generateSecurePassword({int length = 12}) {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#\$%^&*()_+-=';
    final rand = Random.secure();
    return List.generate(length, (index) => chars[rand.nextInt(chars.length)]).join();
  }
}
