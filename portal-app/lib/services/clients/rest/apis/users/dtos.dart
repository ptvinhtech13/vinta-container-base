import 'package:json_annotation/json_annotation.dart';

import '../../../../users/models.dart';

part 'dtos.g.dart';

@JsonSerializable()
class UserResponse {
  String id;
  UserType userType;
  UserStatus userStatus;
  String? phoneNumber;
  String email;
  String fullName;
  String? avatarPath;
  Set<UserRoleResponse> userRoles;
  DateTime createdAt;
  DateTime updatedAt;
  String? createdBy;
  String? updatedBy;

  UserResponse({
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

  factory UserResponse.fromJson(Map<String, dynamic> json) => _$UserResponseFromJson(json);

  Map<String, dynamic> toJson() => _$UserResponseToJson(this);
}

@JsonSerializable()
class UserRoleResponse {
  String tenantId;
  String roleId;

  UserRoleResponse({required this.tenantId, required this.roleId});

  factory UserRoleResponse.fromJson(Map<String, dynamic> json) => _$UserRoleResponseFromJson(json);

  Map<String, dynamic> toJson() => _$UserRoleResponseToJson(this);
}

@JsonSerializable()
class CreateUserBasicAuthRequest {
  final String accessType;
  final String password;

  CreateUserBasicAuthRequest({this.accessType = "BASIC_AUTH", required this.password});

  factory CreateUserBasicAuthRequest.fromJson(Map<String, dynamic> json) => _$CreateUserBasicAuthRequestFromJson(json);

  Map<String, dynamic> toJson() => _$CreateUserBasicAuthRequestToJson(this);
}

@JsonSerializable()
class CreateUserRequest {
  UserType userType;
  String email;
  String fullName;
  String? phoneNumber;
  String roleId;
  CreateUserBasicAuthRequest userAccess;

  CreateUserRequest({
    required this.email,
    required this.fullName,
    this.phoneNumber,
    required this.userType,
    required this.roleId,
    required this.userAccess,
  });

  factory CreateUserRequest.fromJson(Map<String, dynamic> json) => _$CreateUserRequestFromJson(json);

  Map<String, dynamic> toJson() => _$CreateUserRequestToJson(this);
}
