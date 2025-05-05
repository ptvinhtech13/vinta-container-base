import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_widget_from_html/flutter_widget_from_html.dart';

import '../app_page/index.dart';
import 'controller.dart';

class DemoMetabasePage extends AppPage<DemoMetabaseController> {
  final String iframeUrl =
      "https://meta.uat.terabite.sg/embed/dashboard/eyJhbGciOiJIUzI1NiJ9.eyJyZXNvdXJjZSI6eyJkYXNoYm9hcmQiOjE2OX0sInBhcmFtcyI6eyJ0ZW5hbnRfaWQiOiIxMjQ3MTIzOTUyNjI4NDgwMDAifSwiZXhwIjoxNzQ2NTcxMzg1fQ.39giWVUXnMHib-HMmXqbp_l_vMD0QN87XJxHLTpub1c";

  DemoMetabasePage({super.key}) {
    controller.hydrate();
  }

  @override
  Widget buildUI(BuildContext context) {
    return Container(
      height: 1.sh,
      color: Colors.red,
      child: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        physics: const AlwaysScrollableScrollPhysics(),
        child: LayoutBuilder(
          builder: (context, constraints) {
            final minHeight = 1.sh - 50;
            return Column(
              children: [
                HtmlWidget(
                  '''
             <iframe 
                  title="dashboard" 
                  style="flex: 1; border: none; min-height: ${minHeight}px;"
                  src="$iframeUrl#bordered=false&titled=false"
                          ></iframe>
                        ''',
                  customStylesBuilder: (element) {
                    if (element.localName == 'iframe') {
                      return {'display': 'block', 'width': '100%', 'height': '100vh', 'min-height': '${minHeight}px', 'border': 'none'};
                    }
                    return null;
                  },
                ),
              ],
            );
          },
        ),
      ),
    );
  }
}
