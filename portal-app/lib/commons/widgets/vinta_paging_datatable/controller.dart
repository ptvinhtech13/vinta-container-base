import 'dart:developer';

import 'package:async/async.dart';
import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:containerbase/commons/widgets/vinta_paging_datatable/stage.dart';
import 'package:containerbase/pages/tenant_management/state.dart';
import 'package:flutter/src/material/data_table.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/vinta_list_view_ng/models.dart';
import 'package:vinta_shared_commons/index.dart';

class VintaPagingDataTableController<T> extends GetxController {
  final state = VintaPagingDataTableState();

  late DataRow Function(T, List<DataColumnSetting>) dataRowBuilder;
  late Future<PagingResponse<T>> Function(PagingTenantFilter? filterRequest, PageRequest) dataLoader;

  final eventBus = Get.find<InternalEventBusService>();

  CancelableOperation<PagingResponse<T>>? _loadDataTask;

  void hydrate({
    PagingTenantFilter? dataFilter,
    required List<DataColumnSetting> columnSettings,
    required DataRow Function(T, List<DataColumnSetting>) dataRowBuilder,
    required Future<PagingResponse<T>> Function(PagingTenantFilter? filterRequest, PageRequest) dataLoader,
  }) {
    this.state.dataFilter = dataFilter ?? PagingTenantFilter();
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
    _loadDataTask = CancelableOperation.fromFuture(this.dataLoader(state.dataFilter, state.pageRequest.value));
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
}

class ForceRefreshVintaPagingDataTableEvent extends ListViewBaseEvent {
  ForceRefreshVintaPagingDataTableEvent(super.listId);
}
