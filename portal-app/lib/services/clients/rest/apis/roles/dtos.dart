import 'package:json_annotation/json_annotation.dart';

import '../../../../roles/models.dart';
import '../../../../users/models.dart';

part 'dtos.g.dart';

@JsonSerializable()
class RoleResponse {
  final String id;
  final String title;
  final String? roleKey;
  final String? description;
  final List<FeatureNodeResponse>? featureNodes;

  RoleResponse({required this.id, required this.title, this.roleKey, this.description, this.featureNodes});

  factory RoleResponse.fromJson(Map<String, dynamic> json) => _$RoleResponseFromJson(json);

  Map<String, dynamic> toJson() => _$RoleResponseToJson(this);
}

@JsonSerializable()
class FeatureNodeResponse {
  final String id;
  final String? nodeTitle;
  final String nodePath;
  final FeatureNodeType nodeType;
  final int displayOrder;
  final List<UserType>? allowedUserTypes;

  FeatureNodeResponse({
    required this.id,
    this.nodeTitle,
    required this.nodePath,
    required this.nodeType,
    required this.displayOrder,
    this.allowedUserTypes,
  });

  factory FeatureNodeResponse.fromJson(Map<String, dynamic> json) => _$FeatureNodeResponseFromJson(json);

  Map<String, dynamic> toJson() => _$FeatureNodeResponseToJson(this);
}

@JsonSerializable()
class CreateRoleRequest {
  final String title;
  final String? description;
  final List<String>? featureNodeIds;

  CreateRoleRequest({required this.title, this.description, this.featureNodeIds});

  factory CreateRoleRequest.fromJson(Map<String, dynamic> json) => _$CreateRoleRequestFromJson(json);

  Map<String, dynamic> toJson() => _$CreateRoleRequestToJson(this);
}

@JsonSerializable()
class UpdateRoleRequest {
  final String title;
  final String? description;
  final List<String>? featureNodeIds;

  UpdateRoleRequest({required this.title, this.description, this.featureNodeIds});

  factory UpdateRoleRequest.fromJson(Map<String, dynamic> json) => _$UpdateRoleRequestFromJson(json);

  Map<String, dynamic> toJson() => _$UpdateRoleRequestToJson(this);
}
