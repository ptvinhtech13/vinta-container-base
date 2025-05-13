import 'package:containerbase/services/clients/rest/apis/users/dtos.dart';
import 'package:copy_with_extension/copy_with_extension.dart';

import '../roles/models.dart';

part 'models.g.dart';

@CopyWith()
class UserModel {
  String id;
  UserType userType;
  UserStatus userStatus;
  String? phoneNumber;
  String email;
  String fullName;
  String? avatarPath;
  Set<UserRoleModel> userRoles;
  DateTime createdAt;
  DateTime updatedAt;
  String? createdBy;
  String? updatedBy;

  UserModel({
    required this.id,
    required this.userType,
    required this.userStatus,
    this.phoneNumber,
    required this.email,
    required this.fullName,
    this.avatarPath,
    required this.userRoles,
    required this.createdAt,
    required this.updatedAt,
    this.createdBy,
    this.updatedBy,
  });

  static UserModel fromResponse(UserResponse userResponse, Map<String, RoleModel> roleMap) {
    return UserModel(
      id: userResponse.id,
      userType: userResponse.userType,
      userStatus: userResponse.userStatus,
      phoneNumber: userResponse.phoneNumber,
      email: userResponse.email,
      fullName: userResponse.fullName,
      avatarPath: userResponse.avatarPath,
      userRoles: userResponse.userRoles.map((e) => UserRoleModel.fromResponse(e, roleMap[e.roleId]!)).toSet(),
      createdAt: userResponse.createdAt,
      updatedAt: userResponse.updatedAt,
      createdBy: userResponse.createdBy,
      updatedBy: userResponse.updatedBy,
    );
  }
}

enum UserType { SYSTEM_ADMIN, BACK_OFFICE, CUSTOMER }

enum UserStatus { CREATED, ACTIVE, ARCHIVED, DELETING, BLOCKED }

@CopyWith()
class UserRoleModel {
  String tenantId;
  RoleModel roleModel;

  UserRoleModel({required this.tenantId, required this.roleModel});

  static UserRoleModel fromResponse(UserRoleResponse userRoleResponse, RoleModel roleModel) {
    return UserRoleModel(tenantId: userRoleResponse.tenantId, roleModel: roleModel);
  }
}
