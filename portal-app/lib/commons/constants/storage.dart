class SharePreferenceKeys {
  static const String userAuthProfileKey = 'vinta_user_profile';
  static const String localLanguageSettingKey = 'local_language_settings';

  static const String userAuthTokenKey = 'vinta_user_token';
  static const String userAuthRefreshTokenKey = 'vinta_user_refresh_token';
  static const String userAuthEmailKey = 'vinta_user_email';
  static const String userAuthPasswordKey = 'vinta_user_password';
  static const String currentDBAppVersion = "vinta_db_app_version";
  static const String currentApiHostKey = "vinta_current_api_host";

  // static const String storageDeviceFirstOpenKey = 'device_first_open';
  //
  // static const String storageIndexNewsCacheKey = 'cache_index_news';
  //
  // static const String storageLanguageCode = 'language_code';

  static const List<String> removedKeys = [userAuthProfileKey, userAuthTokenKey];
}
