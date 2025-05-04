import 'dart:developer';

import 'package:dio/dio.dart';

class LoggingInterceptor extends Interceptor {
  @override
  Future onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    // Log the request details
    log('${DateTime.now()} --> onRequest ${options.method} ${options.path} ${options.baseUrl}');

    return super.onRequest(options, handler);
  }

  @override
  Future onResponse(Response response, ResponseInterceptorHandler handler) async {
    log('${DateTime.now()} <-- onResponse ${response.statusCode} ${response.requestOptions.method} ${response.requestOptions.path}');
    return super.onResponse(response, handler);
  }

  @override
  Future onError(DioException err, ErrorInterceptorHandler handler) async {
    log('<-- onError ${err.message} ${err.response?.statusCode} ${err.requestOptions.method} ${err.requestOptions.path}');
    log('Body: ${err.response?.data}');
    return super.onError(err, handler);
  }
}
