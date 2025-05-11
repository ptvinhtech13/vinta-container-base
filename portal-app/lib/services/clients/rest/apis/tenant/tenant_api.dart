import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';

import 'dtos.dart';

part 'tenant_api.g.dart';

@RestApi()
abstract class TenantApiClient {
  factory TenantApiClient(Dio dio, {String baseUrl}) = _TenantApiClient;

  @GET("/api/tenant/tenants")
  Future<PagingResponse<TenantResponse>> queryTenants(
    @Query("page") int page,
    @Query("size") int size,
    @Query("filter.byTitle") String? filterByTitle,
  );
}
