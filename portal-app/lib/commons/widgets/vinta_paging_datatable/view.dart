import 'package:collection/collection.dart';
import 'package:containerbase/pages/tenant_management/state.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'controller.dart';
import 'models.dart';

class VintaPagingDataTable<T> extends StatelessWidget {
  late final ScrollController? verticalScrollController;
  late final ScrollController? bodyHorizontalScrollController;
  late final ScrollController? headerHorizontalScrollController;
  late final VintaPagingDataTableController<T> _controller;

  VintaPagingDataTable({
    super.key,
    ScrollController? verticalScrollController,
    ScrollController? bodyHorizontalScrollController,
    ScrollController? headerHorizontalScrollController,
    required List<DataColumnSetting> columnSettings,
    required DataRow Function(T, List<DataColumnSetting>) dataRowBuilder,
    required Future<PagingResponse<T>> Function(PagingTenantFilter? filterRequest, PageRequest) dataLoader,
    PagingTenantFilter? dataFilter,
  }) {
    this.verticalScrollController = verticalScrollController ?? ScrollController();
    this.bodyHorizontalScrollController = bodyHorizontalScrollController ?? ScrollController();
    this.headerHorizontalScrollController = headerHorizontalScrollController ?? ScrollController();

    _controller = Get.put(VintaPagingDataTableController<T>(), tag: 'vinta_paging_data_table');
    _controller.hydrate(dataFilter: dataFilter, columnSettings: columnSettings, dataRowBuilder: dataRowBuilder, dataLoader: dataLoader);
  }

  @override
  Widget build(BuildContext context) {
    return Obx(() {
      return Column(
        children: [
          Expanded(
            child: Align(
              alignment: Alignment.topLeft,
              child: Column(
                children: [
                  // Sticky header
                  Container(
                    color: Colors.grey.shade100,
                    child: SingleChildScrollView(
                      controller: headerHorizontalScrollController,
                      scrollDirection: Axis.horizontal,
                      child: Row(
                        children:
                            _controller.state.columnSettings.where((column) => column.isVisible).sorted((a, b) => a.index.compareTo(b.index)).map((
                              column,
                            ) {
                              return InkWell(
                                onTap: () {},
                                child: Container(
                                  padding: EdgeInsets.symmetric(horizontal: 16, vertical: 16),
                                  decoration: BoxDecoration(
                                    border: Border(
                                      right: BorderSide(color: Colors.grey.shade300, width: 1),
                                      bottom: BorderSide(color: Colors.grey.shade300, width: 1),
                                    ),
                                  ),
                                  child: Row(
                                    children: [
                                      Text(column.label, style: TextStyle(fontWeight: FontWeight.bold)),
                                      SizedBox(width: 4),
                                      // if (isCurrentSortColumn) Icon(isAscending ? Icons.arrow_upward : Icons.arrow_downward, size: 16, color: Colors.grey.shade700),
                                    ],
                                  ),
                                ),
                              );
                              ;
                            }).toList(),
                      ),
                    ),
                  ),
                  // Table body with scrolling
                  Expanded(
                    child: Scrollbar(
                      thumbVisibility: true,
                      controller: verticalScrollController,
                      child: SingleChildScrollView(
                        physics: AlwaysScrollableScrollPhysics(),
                        controller: verticalScrollController,
                        scrollDirection: Axis.vertical,
                        child: Scrollbar(
                          thumbVisibility: true,
                          controller: bodyHorizontalScrollController,
                          scrollbarOrientation: ScrollbarOrientation.bottom,
                          child: SingleChildScrollView(
                            controller: bodyHorizontalScrollController,
                            scrollDirection: Axis.horizontal,
                            child: DataTable(
                              columns:
                                  _controller.state.columnSettings
                                      .where((column) => column.isVisible)
                                      .sorted((a, b) => a.index.compareTo(b.index))
                                      .map((column) {
                                        return DataColumn(
                                          label: Container(
                                            padding: EdgeInsets.symmetric(vertical: 8.0),
                                            child: Text(column.label, style: TextStyle(fontWeight: FontWeight.bold)),
                                          ),
                                          // onSort: (columnIndex, ascending) {
                                          //   _controller.sortByColumn(columnIndex, ascending);
                                          // },
                                        );
                                      })
                                      .toList(),
                              rows:
                                  _controller.state.paginatedDataItem.map((tenant) {
                                    return _controller.dataRowBuilder(tenant, _controller.state.columnSettings);
                                  }).toList(),
                              columnSpacing: 20,
                              headingRowColor: MaterialStateProperty.all(Colors.grey.shade100),
                              dataRowMinHeight: 48,
                              dataRowMaxHeight: 64,
                              // sortColumnIndex: controller.state.sortColumnIndex.value,
                              // sortAscending: controller.state.sortAscending.value,
                              headingRowHeight: 0,
                              // Hide the original header
                              border: TableBorder(
                                bottom: BorderSide(color: Colors.grey.shade300),
                                horizontalInside: BorderSide(color: Colors.grey.shade200),
                              ),
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          // Row(
          //   mainAxisAlignment: MainAxisAlignment.end,
          //   children: [
          //     Row(children: [_buildPaginationControls(), SizedBox(width: 16), _buildColumnOptionsMenu()]),
          //   ],
          // ),
        ],
      );
    });
  }
}
