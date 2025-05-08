import 'dart:developer';

import 'package:containerbase/commons/constants/storage.dart';
import 'package:dio/dio.dart';
import 'package:get/get.dart';
import 'package:pretty_dio_logger/pretty_dio_logger.dart';
import 'package:vinta_shared_commons/repository/simple_repository.dart';
import 'package:vinta_shared_commons/utils/app_utils.dart';

import '../../../../commons/exceptions/error_response.dart';

class ContainerBaseDioService extends GetxService {
  late final Dio _containerBaseDioServer;
  late final Dio _containerBaseDioTokenAccessServer;

  ContainerBaseDioService(SimpleRepository repository) {
    _containerBaseDioServer = setupContainerBaseDioServer(repository);
    _containerBaseDioTokenAccessServer = setupContainerBaseTokenAccessServer(repository);
  }

  Dio get containerBaseDioServer => _containerBaseDioServer;

  Dio get containerBaseDioTokenAccessServer => _containerBaseDioTokenAccessServer;

  Dio setupContainerBaseDioServer(SimpleRepository repository) {
    return Dio(
        BaseOptions(
          receiveDataWhenStatusError: true,
          connectTimeout: const Duration(minutes: 2),
          receiveTimeout: const Duration(minutes: 2),
          sendTimeout: const Duration(minutes: 2),
        ),
      )
      ..interceptors.addAll([
        InterceptorsWrapper(
          onRequest: (options, handler) async {
            var accessToken = repository.getString(SharePreferenceKeys.userAuthTokenKey);
            if (!AppUtils.isEmptyString(accessToken)) {
              options.headers['Authorization'] = "Bearer $accessToken";
            }
            return handler.next(options);
          },
          onResponse: (response, handler) {
            handler.next(response);
          },
          onError: (dioError, handler) {
            try {
              if (dioError.response?.data != null) {
                final errorData = dioError.response!.data;
                if (errorData is Map<String, dynamic>) {
                  final errorResponse = ErrorResponse.fromJson(errorData);
                  dioError = dioError.copyWith(error: errorResponse);
                }
              }
            } catch (e) {
              // If parsing fails, continue with the original error
              log('Error parsing error response: $e');
            }
            handler.next(dioError);
          },
        ),
        PrettyDioLogger(requestHeader: true, requestBody: true, responseHeader: true, responseBody: true),
      ]);
  }

  Dio setupContainerBaseTokenAccessServer(SimpleRepository repository) {
    return Dio(
        BaseOptions(
          receiveDataWhenStatusError: true,
          connectTimeout: const Duration(minutes: 2),
          receiveTimeout: const Duration(minutes: 2),
          sendTimeout: const Duration(minutes: 2),
        ),
      )
      ..interceptors.addAll([
        InterceptorsWrapper(
          onRequest: (options, handler) async {
            var refreshToken = repository.getString(SharePreferenceKeys.userAuthRefreshTokenKey);
            if (!AppUtils.isEmptyString(refreshToken)) {
              options.headers['Authorization'] = "Bearer $refreshToken";
            }
            return handler.next(options);
          },
          onResponse: (response, handler) {
            handler.next(response);
          },
          onError: (dioError, handler) {
            try {
              if (dioError.response?.data != null) {
                final errorData = dioError.response!.data;
                if (errorData is Map<String, dynamic>) {
                  final errorResponse = ErrorResponse.fromJson(errorData);
                  dioError = dioError.copyWith(error: errorResponse);
                }
              }
            } catch (e) {
              // If parsing fails, continue with the original error
              log('Error parsing error response: $e');
            }
            handler.next(dioError);
          },
        ),
        PrettyDioLogger(requestHeader: true, requestBody: true, responseHeader: true, responseBody: true),
      ]);
  }
}
