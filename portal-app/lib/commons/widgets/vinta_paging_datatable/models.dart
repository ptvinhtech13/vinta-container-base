import 'package:copy_with_extension/copy_with_extension.dart';

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

class PagingResponse<T> {
  final List<T> content;
  final int page;
  final int totalElements;
  final int totalPages;

  PagingResponse({required this.content, required this.page, required this.totalElements, required this.totalPages});
}

@CopyWith()
class PageRequest {
  final int page;
  final int size;
  final int totalElements;
  final int totalPages;

  PageRequest({required this.page, required this.size, required this.totalElements, required this.totalPages});
}
