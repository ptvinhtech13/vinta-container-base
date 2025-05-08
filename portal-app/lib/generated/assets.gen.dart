/// GENERATED CODE - DO NOT MODIFY BY HAND
/// *****************************************************
///  FlutterGen
/// *****************************************************

// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: directives_ordering,unnecessary_import,implicit_dynamic_list_literal,deprecated_member_use

import 'package:flutter/widgets.dart';
import 'package:lottie/lottie.dart' as _lottie;

class $AssetsIconsGen {
  const $AssetsIconsGen();

  /// File path: assets/icons/button_state_error.json
  LottieGenImage get buttonStateError => const LottieGenImage('assets/icons/button_state_error.json');

  /// File path: assets/icons/button_state_info.json
  LottieGenImage get buttonStateInfo => const LottieGenImage('assets/icons/button_state_info.json');

  /// File path: assets/icons/button_state_success.json
  LottieGenImage get buttonStateSuccess => const LottieGenImage('assets/icons/button_state_success.json');

  /// File path: assets/icons/button_state_warning.json
  LottieGenImage get buttonStateWarning => const LottieGenImage('assets/icons/button_state_warning.json');

  /// File path: assets/icons/lottie-ship-container.json
  LottieGenImage get lottieShipContainer => const LottieGenImage('assets/icons/lottie-ship-container.json');

  /// File path: assets/icons/progress_loading.json
  LottieGenImage get progressLoading => const LottieGenImage('assets/icons/progress_loading.json');

  /// File path: assets/icons/progress_loading_2.json
  LottieGenImage get progressLoading2 => const LottieGenImage('assets/icons/progress_loading_2.json');

  /// List of all assets
  List<LottieGenImage> get values => [
    buttonStateError,
    buttonStateInfo,
    buttonStateSuccess,
    buttonStateWarning,
    lottieShipContainer,
    progressLoading,
    progressLoading2,
  ];
}

class Assets {
  const Assets._();

  static const $AssetsIconsGen icons = $AssetsIconsGen();
}

class LottieGenImage {
  const LottieGenImage(this._assetName, {this.flavors = const {}});

  final String _assetName;
  final Set<String> flavors;

  _lottie.LottieBuilder lottie({
    Animation<double>? controller,
    bool? animate,
    _lottie.FrameRate? frameRate,
    bool? repeat,
    bool? reverse,
    _lottie.LottieDelegates? delegates,
    _lottie.LottieOptions? options,
    void Function(_lottie.LottieComposition)? onLoaded,
    _lottie.LottieImageProviderFactory? imageProviderFactory,
    Key? key,
    AssetBundle? bundle,
    Widget Function(BuildContext, Widget, _lottie.LottieComposition?)? frameBuilder,
    ImageErrorWidgetBuilder? errorBuilder,
    double? width,
    double? height,
    BoxFit? fit,
    AlignmentGeometry? alignment,
    String? package,
    bool? addRepaintBoundary,
    FilterQuality? filterQuality,
    void Function(String)? onWarning,
    _lottie.LottieDecoder? decoder,
    _lottie.RenderCache? renderCache,
    bool? backgroundLoading,
  }) {
    return _lottie.Lottie.asset(
      _assetName,
      controller: controller,
      animate: animate,
      frameRate: frameRate,
      repeat: repeat,
      reverse: reverse,
      delegates: delegates,
      options: options,
      onLoaded: onLoaded,
      imageProviderFactory: imageProviderFactory,
      key: key,
      bundle: bundle,
      frameBuilder: frameBuilder,
      errorBuilder: errorBuilder,
      width: width,
      height: height,
      fit: fit,
      alignment: alignment,
      package: package,
      addRepaintBoundary: addRepaintBoundary,
      filterQuality: filterQuality,
      onWarning: onWarning,
      decoder: decoder,
      renderCache: renderCache,
      backgroundLoading: backgroundLoading,
    );
  }

  String get path => _assetName;

  String get keyName => _assetName;
}
