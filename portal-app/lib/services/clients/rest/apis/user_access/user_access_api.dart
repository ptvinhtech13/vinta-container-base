import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';

import 'dtos.dart';

part 'user_access_api.g.dart';

@RestApi()
abstract class UserAccessApiClient {
  factory UserAccessApiClient(Dio dio, {String baseUrl}) = _UserAccessApiClient;

  @POST("/api/user/access/login")
  Future<LoginResponse> login(@Body() LoginRequest request);

  @POST("/api/user/access/refresh")
  Future<LoginResponse> refreshToken();
}
