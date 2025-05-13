import 'package:copy_with_extension/copy_with_extension.dart';

part 'requests.g.dart';

@CopyWith()
class UserRoleFilter {
  final Set<String>? byRoleIds;
  final Set<String>? byRoleKeys;

  UserRoleFilter({this.byRoleIds, this.byRoleKeys});
}
