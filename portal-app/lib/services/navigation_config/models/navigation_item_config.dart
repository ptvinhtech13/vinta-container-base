import 'package:json_annotation/json_annotation.dart';

part 'navigation_item_config.g.dart';

@JsonSerializable()
class NavigationItemConfig {
  final int displayOrder;
  final int? iconCode;
  final bool? isLogout;

  final String title;
  final String? route;
  final List<NavigationItemConfig> children;

  NavigationItemConfig(this.title, {this.displayOrder = 0, this.iconCode = 0xf522, this.route, this.children = const [], this.isLogout = false});

  factory NavigationItemConfig.fromJson(Map<String, dynamic> json) => _$NavigationItemConfigFromJson(json);

  Map<String, dynamic> toJson() => _$NavigationItemConfigToJson(this);
}
