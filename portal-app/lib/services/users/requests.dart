import 'package:copy_with_extension/copy_with_extension.dart';

import '../../commons/domains/filters.dart';

part 'requests.g.dart';

@CopyWith()
class UserFilter {
  final String? byUserId;
  final String? byTenantId;
  final Set<String>? byRoleIds;
  final Set<String>? byUserTypes;
  final Set<String>? byUserStatuses;
  final Set<String>? byUserIds;
  final String? byPhoneNumber;
  final String? byEmail;
  final String? byContainingEmail;
  final String? byName;
  final DateTimeRangeFilter? byCreatedRange;

  UserFilter({
    this.byUserId,
    this.byTenantId,
    this.byRoleIds,
    this.byUserTypes,
    this.byUserStatuses,
    this.byUserIds,
    this.byPhoneNumber,
    this.byEmail,
    this.byContainingEmail,
    this.byName,
    this.byCreatedRange,
  });
}
