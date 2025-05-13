import 'package:containerbase/services/tenant/index.dart';

class DomainConstants {
  static TenantModel defaultTenant = TenantModel(
    id: '88888888',
    title: 'Admin Tenant',
    description: 'Admin Tenant',
    domainHost: '',
    status: TenantStatus.ACTIVE,
    createdAt: DateTime.now(),
    updatedAt: DateTime.now(),
  );
}

class AppSlidingPanel {
  static const double animatedPosition = 0.45;
}
