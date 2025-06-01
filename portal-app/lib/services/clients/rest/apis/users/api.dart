import 'package:containerbase/commons/widgets/vinta_paging_datatable/models.dart';
import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';

import 'dtos.dart';

part 'api.g.dart';

@RestApi()
abstract class UserApiClient {
  factory UserApiClient(Dio dio, {String baseUrl}) = _UserApiClient;

  @GET("/api/user/users")
  Future<PagingResponse<UserResponse>> queryUsers(
    @Query("page") int page,
    @Query("size") int size, {
    @Query("filter.byUserId") String? byUserId,
    @Query("filter.byTenantId") String? byTenantId,
    @Query("filter.byRoleIds") List<String>? byRoleIds,
    @Query("filter.byUserTypes") List<String>? byUserTypes,
    @Query("filter.byUserStatuses") List<String>? byUserStatuses,
    @Query("filter.byUserIds") List<String>? byUserIds,
    @Query("filter.byPhoneNumber") String? byPhoneNumber,
    @Query("filter.byEmail") String? byEmail,
    @Query("filter.byContainingEmail") String? byContainingEmail,
    @Query("filter.byName") String? byName,
    @Query("filter.byCreatedRange.from") DateTime? byCreatedRangeFrom,
    @Query("filter.byCreatedRange.to") DateTime? byCreatedRangeTo,

    @Query("sortFields") String? sortFields,
    @Query("sortDirection") String? sortDirection,
  });

  @GET("/api/user/users/me")
  Future<UserResponse> getUserMe();

  @POST("/api/user/users")
  Future<UserResponse> createUser(@Body() CreateUserRequest request);
}
