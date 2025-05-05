import 'package:json_annotation/json_annotation.dart';

part 'navigation_item_config.g.dart';

@JsonSerializable()
class NavigationItem {
  final int displayOrder;
  final int? iconCode;

  final String title;
  final String? route;
  final List<NavigationItem> children;

  NavigationItem(this.title, {this.displayOrder = 0, this.iconCode = 0xf522, this.route, this.children = const []});

  factory NavigationItem.fromJson(Map<String, dynamic> json) => _$NavigationItemFromJson(json);

  Map<String, dynamic> toJson() => _$NavigationItemToJson(this);
}
