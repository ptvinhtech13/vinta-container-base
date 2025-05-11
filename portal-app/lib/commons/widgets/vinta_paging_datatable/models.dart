import 'package:copy_with_extension/copy_with_extension.dart';
import 'package:json_annotation/json_annotation.dart';

part 'models.g.dart';

@CopyWith()
class DataColumnSetting {
  final int index;
  final String label;
  final String columnKey;
  final bool isVisible;
  final bool isSortable;

  DataColumnSetting({required this.index, required this.label, required this.columnKey, this.isVisible = true, this.isSortable = false});
}

@CopyWith()
@JsonSerializable(genericArgumentFactories: true)
class PagingResponse<T> {
  final List<T> content;
  final int page;
  final int totalElements;
  final int totalPages;

  PagingResponse({required this.content, required this.page, required this.totalElements, required this.totalPages});

  factory PagingResponse.fromJson(Map<String, dynamic> json, T Function(Object? json) fromJsonT) => _$PagingResponseFromJson(json, fromJsonT);

  Map<String, dynamic> toJson(Object? Function(T value) toJsonT) => _$PagingResponseToJson(this, toJsonT);
}

@CopyWith()
class PageRequest<T> {
  final T? filter;
  final int page;
  final int size;
  final int totalElements;
  final int totalPages;

  PageRequest({required this.page, required this.size, required this.totalElements, required this.totalPages, this.filter});
}
