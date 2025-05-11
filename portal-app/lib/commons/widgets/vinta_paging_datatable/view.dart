import 'package:collection/collection.dart';
import 'package:data_table_2/data_table_2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../pages/app_layout/controller.dart';
import '../../constants/colors.dart';
import 'controller.dart';
import 'models.dart';

class VintaPagingDataTable<Model, Filter> extends StatelessWidget {
  late final ScrollController? _scrollController;
  late final VintaPagingDataTableController<Model, Filter> _controller;
  final appPageController = Get.find<AppPageController>();

  VintaPagingDataTable({
    super.key,
    String? dataTableKey,
    ScrollController? scrollController,
    required List<DataColumnSetting> columnSettings,
    required DataRow Function(Model, List<DataColumnSetting>) dataRowBuilder,
    required Future<PagingResponse<Model>> Function(PageRequest<Filter?>) dataLoader,
    Filter? filter,
    String? sortFields,
    String? sortDirection,
  }) {
    this._scrollController = scrollController ?? ScrollController();

    _controller = Get.put(VintaPagingDataTableController<Model, Filter>(), tag: dataTableKey);
    _controller.hydrate(
      filter: filter,
      columnSettings: columnSettings,
      dataRowBuilder: dataRowBuilder,
      dataLoader: dataLoader,
      sortFields: sortFields,
      sortDirection: sortDirection,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Obx(() {
      final visibleColumns =
          _controller.state.columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).toList();
      return Column(
        children: [
          Expanded(
            child: Align(
              alignment: Alignment.topLeft,
              child: Column(
                children: [
                  // Table body with scrolling
                  Expanded(
                    child: DataTable2(
                      dividerThickness: 1,
                      scrollController: _scrollController,
                      columnSpacing: 5,
                      headingRowColor: WidgetStateProperty.resolveWith((states) => Colors.greenAccent),
                      headingRowDecoration: BoxDecoration(
                        borderRadius: BorderRadius.vertical(top: Radius.circular(10)),
                        gradient: LinearGradient(
                          colors: [AppColors.colorPrimary01, AppColors.colorPrimary02],
                          begin: Alignment.topCenter,
                          end: Alignment.bottomCenter,
                        ),
                      ),
                      columns:
                          visibleColumns.map((column) {
                            final isCurrentSortColumn = _controller.state.sortedColumnKey.value == column.columnKey;
                            final isAscending = _controller.state.isAscending.value;
                            final isHovered = _controller.state.hoveredColumnKey.value == column.columnKey;
                            return DataColumn2(
                              size: column.size,
                              onSort: column.isSortable ? (_, __) => _controller.sortByColumn(column.columnKey) : null,
                              label: MouseRegion(
                                onEnter: (_) => column.isSortable ? _controller.setHoveredColumn(column.columnKey) : null,
                                onExit: (_) => column.isSortable ? _controller.clearHoveredColumn() : null,
                                child: Container(
                                  padding: EdgeInsets.symmetric(vertical: 8.0),
                                  child: Row(
                                    mainAxisSize: MainAxisSize.min,
                                    children: [
                                      Text(
                                        column.label,
                                        style: TextStyle(fontSize: 14, color: AppColors.colorPrimary11, fontWeight: FontWeight.w900),
                                      ),
                                      if (column.isSortable) SizedBox(width: 4),
                                      if (column.isSortable && (isCurrentSortColumn || isHovered))
                                        Icon(
                                          isCurrentSortColumn ? (isAscending ? Icons.arrow_upward : Icons.arrow_downward) : Icons.unfold_more,
                                          size: 16,
                                          color: AppColors.colorPrimary11,
                                        ),
                                    ],
                                  ),
                                ),
                              ),
                            );
                          }).toList(),
                      rows:
                          _controller.state.paginatedDataItem.map((item) {
                            return _controller.dataRowBuilder(item, _controller.state.columnSettings);
                          }).toList(),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Row(
                children: [
                  _buildPaginationControls(),
                  // SizedBox(width: 16),
                  // _buildColumnOptionsMenu()
                ],
              ),
            ],
          ),
        ],
      );
    });
  }

  Widget _buildPaginationControls() {
    return Row(
      children: [
        Container(
          padding: EdgeInsets.symmetric(horizontal: 8),
          decoration: BoxDecoration(border: Border.all(color: Colors.grey.shade300), borderRadius: BorderRadius.circular(4)),
          child: DropdownButton<int>(
            value: _controller.state.pageRequest.value.size,
            underline: SizedBox(),
            items:
                [20, 100, 500].map((size) {
                  return DropdownMenuItem<int>(value: size, child: Text('$size items'));
                }).toList(),
            onChanged: (value) {
              if (value != null) {
                FocusManager.instance.primaryFocus?.unfocus();
                _controller.changePageSize(value);
              }
            },
          ),
        ),
        SizedBox(width: 16),
        Text(
          'Showing ${(_controller.state.pageRequest.value.page) * _controller.state.pageRequest.value.size + 1} - ${(_controller.state.pageRequest.value.page + 1) * _controller.state.pageRequest.value.size} of ${_controller.state.pageRequest.value.totalElements} items',
        ),
        // Pagination buttons
        IconButton(
          icon:
              _controller.state.pageRequest.value.page > 0
                  ? Icon(Icons.navigate_before, color: Colors.grey.shade900)
                  : Icon(Icons.navigate_before, color: Colors.grey.shade300),
          onPressed: _controller.state.pageRequest.value.page > 0 ? () => _controller.goToPage(_controller.state.pageRequest.value.page - 1) : null,
          tooltip: 'Previous Page',
        ),
        Container(
          padding: EdgeInsets.symmetric(horizontal: 12),
          child: Text(
            '${_controller.state.pageRequest.value.page + 1} / ${_controller.state.pageRequest.value.totalPages}',
            style: TextStyle(fontWeight: FontWeight.bold),
          ),
        ),
        IconButton(
          icon:
              _controller.state.pageRequest.value.page < _controller.state.pageRequest.value.totalPages - 1
                  ? Icon(Icons.navigate_next, color: Colors.grey.shade900)
                  : Icon(Icons.navigate_next, color: Colors.grey.shade300),
          onPressed:
              _controller.state.pageRequest.value.page < _controller.state.pageRequest.value.totalPages - 1
                  ? () => _controller.goToPage(_controller.state.pageRequest.value.page + 1)
                  : null,
          tooltip: 'Next Page',
        ),
      ],
    );
  }
}
