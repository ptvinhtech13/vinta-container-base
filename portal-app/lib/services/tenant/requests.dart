import 'package:copy_with_extension/copy_with_extension.dart';

part 'requests.g.dart';

@CopyWith()
class TenantFilter {
  final String? byName;
  final String? byStatuses;

  TenantFilter({this.byName, this.byStatuses});
}
