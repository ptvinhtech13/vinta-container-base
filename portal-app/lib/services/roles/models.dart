import 'package:containerbase/services/clients/rest/apis/roles/dtos.dart';
import 'package:copy_with_extension/copy_with_extension.dart';

import '../users/models.dart';

part 'models.g.dart';

@CopyWith()
class RoleModel {
  String id;
  String title;
  String? roleKey;
  String? description;
  Set<FeatureNodeModel> featureNodes;

  RoleModel({required this.id, required this.title, this.roleKey, this.description, required this.featureNodes});

  static RoleModel fromResponse(RoleResponse roleResponse) {
    return RoleModel(
      id: roleResponse.id,
      title: roleResponse.title,
      roleKey: roleResponse.roleKey,
      description: roleResponse.description,
      featureNodes: roleResponse.featureNodes?.map((e) => FeatureNodeModel.fromResponse(e)).toSet() ?? {},
    );
  }
}

@CopyWith()
class FeatureNodeModel {
  String id;
  String? nodeTitle;
  String nodePath;
  FeatureNodeType nodeType;
  int displayOrder;
  Set<UserType> allowedUserTypes;

  FeatureNodeModel({
    required this.id,
    this.nodeTitle,
    required this.nodePath,
    required this.nodeType,
    required this.displayOrder,
    required this.allowedUserTypes,
  });

  static FeatureNodeModel fromResponse(FeatureNodeResponse e) {
    return FeatureNodeModel(
      id: e.id,
      nodeTitle: e.nodeTitle,
      nodePath: e.nodePath,
      nodeType: e.nodeType,
      displayOrder: e.displayOrder,
      allowedUserTypes: e.allowedUserTypes?.toSet() ?? {},
    );
  }
}

enum FeatureNodeType { MODULE, API }
