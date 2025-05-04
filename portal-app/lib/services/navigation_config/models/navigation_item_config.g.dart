// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'navigation_item_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

NavigationItemConfig _$NavigationItemConfigFromJson(
        Map<String, dynamic> json) =>
    NavigationItemConfig(
      json['title'] as String,
      displayOrder: (json['displayOrder'] as num?)?.toInt() ?? 0,
      iconCode: (json['iconCode'] as num?)?.toInt() ?? 0xf522,
      route: json['route'] as String?,
      children: (json['children'] as List<dynamic>?)
              ?.map((e) =>
                  NavigationItemConfig.fromJson(e as Map<String, dynamic>))
              .toList() ??
          const [],
      isLogout: json['isLogout'] as bool? ?? false,
    );

Map<String, dynamic> _$NavigationItemConfigToJson(
        NavigationItemConfig instance) =>
    <String, dynamic>{
      'displayOrder': instance.displayOrder,
      'iconCode': instance.iconCode,
      'isLogout': instance.isLogout,
      'title': instance.title,
      'route': instance.route,
      'children': instance.children,
    };
