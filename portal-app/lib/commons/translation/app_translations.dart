import 'package:get/get.dart';
import 'package:vinta_shared_commons/index.dart';

import '../app_exceptions.dart';

class AppTranslations extends Translations {
  @override
  Map<String, Map<String, String>> get keys => {
    'en_US': {"SHARE_RETRY_APP_WARNING_ERROR": "Something is wrong. Under retrying...", "LIST_VIEW_LOADED": "Results"},
    'vi_VN': {"SHARE_RETRY_APP_WARNING_ERROR": "Chờ xíu lỗi kết nối. Đang thử lại...", "LIST_VIEW_LOADED": "Results"},
  };
}

class SupportedLanguageConstants {
  static List<SupportedLanguageModel> i8nSupports = [SupportedLanguageModel("en_US", "English"), SupportedLanguageModel("vi_VN", "Vietnamese")];

  static SupportedLanguageModel getVintaI8n({String? localeCode, String? title}) {
    if (!AppUtils.isEmptyString(localeCode)) {
      return i8nSupports.where((element) => element.localeCode == localeCode).first;
    }

    if (!AppUtils.isEmptyString(title)) {
      return i8nSupports.where((element) => element.titleLanguage == title).first;
    }

    throw AppBaseException("not found i8n support");
  }
}

class SupportedLanguageModel {
  final String localeCode;
  final String titleLanguage;

  SupportedLanguageModel(this.localeCode, this.titleLanguage);

  @override
  bool operator ==(Object other) => identical(this, other) || other is SupportedLanguageModel && localeCode == other.localeCode;

  @override
  int get hashCode => localeCode.hashCode;
}
