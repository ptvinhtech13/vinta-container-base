import 'package:json_annotation/json_annotation.dart';

part 'dtos.g.dart';

@JsonSerializable()
class LoginRequest {
  final String accessType;
  final String email;
  final String password;

  LoginRequest({required this.accessType, required this.email, required this.password});

  factory LoginRequest.fromJson(Map<String, dynamic> json) => _$LoginRequestFromJson(json);

  Map<String, dynamic> toJson() => _$LoginRequestToJson(this);
}

@JsonSerializable()
class UserAccessTokenResponse {
  final DateTime expiresAt;
  final String token;

  UserAccessTokenResponse({required this.expiresAt, required this.token});

  factory UserAccessTokenResponse.fromJson(Map<String, dynamic> json) => _$UserAccessTokenResponseFromJson(json);

  Map<String, dynamic> toJson() => _$UserAccessTokenResponseToJson(this);
}

@JsonSerializable()
class LoginResponse {
  final UserAccessTokenResponse accessToken;
  final UserAccessTokenResponse refreshToken;

  LoginResponse({required this.accessToken, required this.refreshToken});

  factory LoginResponse.fromJson(Map<String, dynamic> json) => _$LoginResponseFromJson(json);

  Map<String, dynamic> toJson() => _$LoginResponseToJson(this);
}
