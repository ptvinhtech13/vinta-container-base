import 'dart:developer';

import 'package:async/async.dart';
import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/commons/widgets/vinta_paging_datatable/stage.dart';
import 'package:flutter/src/material/data_table.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/vinta_list_view_ng/models.dart';
import 'package:vinta_shared_commons/index.dart';

class VintaPagingDataTableController<Model, Filter> extends GetxController {
  final state = VintaPagingDataTableState<Model, Filter>();

  late DataRow Function(Model, List<DataColumnSetting>) dataRowBuilder;
  late Future<PagingResponse<Model>> Function(PageRequest<Filter?>) dataLoader;

  final eventBus = Get.find<InternalEventBusService>();

  CancelableOperation<PagingResponse<Model>>? _loadDataTask;

  void hydrate({
    Filter? filter,
    required List<DataColumnSetting> columnSettings,
    required DataRow Function(Model, List<DataColumnSetting>) dataRowBuilder,
    required Future<PagingResponse<Model>> Function(PageRequest<Filter?>) dataLoader,
    String? initialSortedField,
    String? initialSortDirection,
  }) {
    // Set initial sort parameters if provided
    if (initialSortedField != null) {
      state.sortedColumnKey.value = initialSortedField;
      state.isAscending.value = initialSortDirection != "DESC";
    }

    this.state.pageRequest.value = this.state.pageRequest.value.copyWith(
      filter: filter,
      sortedField: initialSortedField,
      sortDirection: initialSortDirection,
    );

    this.dataLoader = dataLoader;
    this.dataRowBuilder = dataRowBuilder;
    this.state.columnSettings.clear();
    this.state.columnSettings.addAll(columnSettings);
  }

  @override
  Future<void> onReady() async {
    super.onReady();
    eventBus.onEvent.where((event) => event is ForceRefreshVintaPagingDataTableEvent).cast<ForceRefreshVintaPagingDataTableEvent>().listen((
      event,
    ) async {
      log("Received ${event.runtimeType}");
      await updatePaginationTable();
    });
    await updatePaginationTable();
  }

  Future<void> updatePaginationTable() async {
    await _loadDataTask?.cancel();
    _loadDataTask = CancelableOperation.fromFuture(this.dataLoader(state.pageRequest.value));
    _loadDataTask!.value.then((result) async {
      this.state.pageRequest.value = this.state.pageRequest.value.copyWith(
        page: result.page,
        totalElements: result.totalElements,
        totalPages: result.totalPages,
      );
      this.state.paginatedDataItem.clear();
      this.state.paginatedDataItem.addAll(result.content);
    });
  }

  void goToPage(int page) {
    if (page < 0 || page >= state.pageRequest.value.totalPages) return;

    state.pageRequest.value = state.pageRequest.value.copyWith(page: page);
    updatePaginationTable();
  }

  void changePageSize(int size) {
    if (size < 0 || size > 500) return;

    state.pageRequest.value = state.pageRequest.value.copyWith(size: size);
    updatePaginationTable();
  }

  void sortByColumn(String columnKey) {
    // If clicking the same column, toggle sort direction
    if (state.sortedColumnKey.value == columnKey) {
      state.isAscending.value = !state.isAscending.value;
    } else {
      // New column, default to ascending
      state.sortedColumnKey.value = columnKey;
      state.isAscending.value = true;
    }

    // Update page request with sort parameters
    state.pageRequest.value = state.pageRequest.value.copyWith(sortedField: columnKey, sortDirection: state.isAscending.value ? "ASC" : "DESC");

    // Reload data with new sort parameters
    updatePaginationTable();
  }
}

class ForceRefreshVintaPagingDataTableEvent extends ListViewBaseEvent {
  ForceRefreshVintaPagingDataTableEvent(super.listId);
}
