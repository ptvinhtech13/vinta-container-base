import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';

import 'dtos.dart';

part 'api.g.dart';

@RestApi()
abstract class RoleApiClient {
  factory RoleApiClient(Dio dio, {String baseUrl}) = _RoleApiClient;

  @GET("/api/role/roles")
  Future<PagingResponse<RoleResponse>> queryRoles(
    @Query("page") int page,
    @Query("size") int size, {
    @Query("filter.byRoleIds") List<String>? byRoleIds,
    @Query("filter.byRoleKeys") List<String>? byRoleKeys,
    @Query("filter.byTenantId") String? byTenantId,

    @Query("sortFields") String? sortFields,
    @Query("sortDirection") String? sortDirection,
  });
}
