import 'package:json_annotation/json_annotation.dart';

part 'error_response.g.dart';

@JsonSerializable()
class ErrorDetail {
  final String message;
  final String field;
  final String rejectedValue;

  ErrorDetail({required this.message, required this.field, required this.rejectedValue});

  factory ErrorDetail.fromJson(Map<String, dynamic> json) => _$ErrorDetailFromJson(json);

  Map<String, dynamic> toJson() => _$ErrorDetailToJson(this);
}

@JsonSerializable()
class ErrorResponse {
  final int errorCode;
  final String message;
  final String path;
  final String timestamp;
  final Map<String, String>? optionalData;
  final List<ErrorDetail>? details;

  ErrorResponse({required this.errorCode, required this.message, required this.path, required this.timestamp, this.optionalData, this.details});

  factory ErrorResponse.fromJson(Map<String, dynamic> json) => _$ErrorResponseFromJson(json);

  Map<String, dynamic> toJson() => _$ErrorResponseToJson(this);
}
