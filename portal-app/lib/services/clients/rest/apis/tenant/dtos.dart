import 'package:json_annotation/json_annotation.dart';

import '../../../../tenant/models.dart';

part 'dtos.g.dart';

@JsonSerializable()
class TenantResponse {
  final String id;
  final String title;
  final String? description;
  final String domainHost;
  final TenantStatus status;
  final DateTime createdAt;
  final DateTime updatedAt;

  TenantResponse({
    required this.id,
    required this.title,
    this.description,
    required this.domainHost,
    required this.status,
    required this.createdAt,
    required this.updatedAt,
  });

  factory TenantResponse.fromJson(Map<String, dynamic> json) => _$TenantResponseFromJson(json);

  Map<String, dynamic> toJson() => _$TenantResponseToJson(this);
}
