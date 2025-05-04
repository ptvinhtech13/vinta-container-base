import 'package:containerbase/commons/constants/colors.dart';
import 'package:easy_debounce/easy_debounce.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:vinta_shared_commons/components/index.dart';
import 'package:vinta_shared_commons/constants/radii.dart';
import 'package:vinta_shared_commons/constants/spaces.dart';
import 'package:vinta_shared_commons/constants/texts.dart';
import 'package:vinta_shared_commons/notifications/index.dart';
import 'package:vinta_shared_commons/utils/index.dart';

import '../../commons/routes/app_routes.dart';
import '../../generated/assets.gen.dart' as app;
import '../../services/user_authentication/service.dart';
import '../app_page/index.dart';
import 'controller.dart';

class WelcomePage extends AppPage<WelcomeController> {
  final userAuthenticationService = Get.find<UserAuthenticationService>();
  final appNotificationManager = Get.find<AppNotificationCenterManager>();

  final _formKey = GlobalKey<FormState>();

  final isValidEmail = false.obs;
  final isValidPassword = false.obs;
  final isEnabledLoginButton = false.obs;

  WelcomePage({super.key, super.showNavigationSideBar = false, super.showAppBar = false}) {
    controller.hydrate();
  }

  @override
  Widget buildUI(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusManager.instance.primaryFocus?.unfocus();
      },
      child: SingleChildScrollView(
        child: SizedBox(
          width: 1.sw,
          child: Column(
            children: [
              SizedBox(
                height: 350,
                width: 1.sw,
                child: Stack(
                  alignment: Alignment.center,
                  children: [app.Assets.icons.lottieShipContainer.lottie(height: 300, width: 300, repeat: true)],
                ),
              ),
              Obx(
                () => Visibility(
                  visible: controller.isVisibleLogin.value,
                  child: AnimatedOpacity(
                    opacity: controller.isOpenLogin.isTrue ? 1 : 0,
                    curve: Curves.bounceInOut,
                    duration: const Duration(milliseconds: 1500),
                    child: AnimatedSize(
                      duration: const Duration(milliseconds: 1000),
                      curve: Curves.fastOutSlowIn,
                      child: SizedBox(
                        width: ResponsiveWidget.getResponsiveValue(context, smallProvider: () => 0.8.sw, defaultProvider: () => 350),
                        child: Form(
                          key: _formKey,
                          child: Column(
                            children: [
                              AppInputField(
                                key: const Key("login_email"),
                                primaryColor: AppColors.colorPrimary01,
                                cursorColor: AppColors.colorPrimary01,
                                textInputAction: TextInputAction.newline,
                                controller: controller.emailController,
                                scrollPadding: const EdgeInsets.only(bottom: 230),
                                minLines: 1,
                                maxLines: 1,
                                style: AppTextStyles.textTitleMedium2,
                                decoration: InputDecoration(hintText: "Email", hintStyle: AppTextStyles.textBodyLarge.copyWith(color: Colors.grey)),
                                onChanged: (text) {
                                  EasyDebounce.debounce('email-change-debouncer', const Duration(milliseconds: 300), () async {
                                    isValidEmail.value = AppUtils.isEmail(text);
                                  });
                                },
                                validator: (input) {
                                  if (input == null || input.isEmpty) {
                                    return "Please enter email";
                                  }
                                  return null;
                                },
                              ),
                              AppSpaces.spaceH10,
                              AppInputField(
                                key: const Key("login_passwords"),
                                primaryColor: AppColors.colorPrimary01,
                                cursorColor: AppColors.colorPrimary01,
                                textInputAction: TextInputAction.go,
                                controller: controller.passwordController,
                                scrollPadding: const EdgeInsets.only(bottom: 230),
                                minLines: 1,
                                maxLines: 1,
                                style: AppTextStyles.textTitleMedium2,
                                obscureText: true,
                                decoration: InputDecoration(
                                  hintText: "Passwords",
                                  hintStyle: AppTextStyles.textBodyLarge.copyWith(color: Colors.grey),
                                ),
                                validator: (input) {
                                  if (input == null || input.isEmpty) {
                                    return "Please enter OTP code";
                                  }
                                  return null;
                                },
                                onChanged: (text) {
                                  EasyDebounce.debounce('password-change-debouncer', const Duration(milliseconds: 300), () async {
                                    isValidEmail.value = AppUtils.isEmail(text);
                                  });
                                },
                              ),
                              AppSpaces.spaceH10,
                              SizedBox(
                                width: 100,
                                child: ElevatedButton(
                                  style: ButtonStyle(
                                    padding: WidgetStateProperty.all(const EdgeInsets.symmetric(horizontal: 10, vertical: 5)),
                                    backgroundColor: WidgetStateProperty.all(AppColors.colorPrimary01),
                                    shape: WidgetStateProperty.all<RoundedRectangleBorder>(
                                      const RoundedRectangleBorder(borderRadius: Radii.radiusK15),
                                    ),
                                  ),
                                  onPressed: () async {
                                    FocusManager.instance.primaryFocus?.unfocus();
                                    if (!_formKey.currentState!.validate()) {
                                      return;
                                    }
                                    final username = controller.emailController.text.trim();
                                    final password = controller.passwordController.text.trim();

                                    controller.userAuthService.login(username, password).then((value) {
                                      Get.offAndToNamed(AppRoutes.home);
                                    });
                                  },
                                  child: Text(
                                    "Login",
                                    style: AppTextStyles.textTitleMedium2.copyWith(color: Colors.white, fontWeight: FontWeight.w600),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
