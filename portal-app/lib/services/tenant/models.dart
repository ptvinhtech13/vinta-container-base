import 'package:containerbase/services/clients/rest/apis/tenant/dtos.dart';
import 'package:copy_with_extension/copy_with_extension.dart';

part 'models.g.dart';

enum TenantStatus { CREATED, ACTIVE, INACTIVE }

@CopyWith()
class TenantModel {
  final String id;
  final String title;
  final String? description;
  final String domainHost;
  final TenantStatus status;
  final DateTime createdAt;
  final DateTime updatedAt;

  TenantModel({
    required this.id,
    required this.title,
    this.description,
    required this.domainHost,
    required this.status,
    required this.createdAt,
    required this.updatedAt,
  });

  static TenantModel fromTenantResponse(TenantResponse tenant) {
    return TenantModel(
      id: tenant.id,
      title: tenant.title,
      description: tenant.description,
      domainHost: tenant.domainHost,
      status: tenant.status,
      createdAt: tenant.createdAt,
      updatedAt: tenant.updatedAt,
    );
  }
}
