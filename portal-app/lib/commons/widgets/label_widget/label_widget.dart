import 'package:flutter/material.dart';
import 'package:vinta_shared_commons/index.dart';

class LabelWidget extends StatelessWidget {
  const LabelWidget({
    super.key,
    required this.child,
    required this.label,
    this.isRequired = false,
    this.padding = EdgeInsets.zero,
    this.space,
    this.labelFontSize = 16,
  });

  final String label;
  final double? labelFontSize;
  final bool isRequired;
  final Widget child;
  final EdgeInsetsGeometry padding;
  final Widget? space;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            Padding(
              padding: padding,
              child: Text(label, style: TextStyle(fontSize: labelFontSize, fontWeight: FontWeight.normal, color: Colors.grey.shade900)),
            ),
            AppSpaces.spaceH6,
            Visibility(visible: isRequired, child: const Text('*', style: TextStyle(fontSize: 16, fontWeight: FontWeight.normal, color: Colors.red))),
          ],
        ),
        space ?? AppSpaces.spaceH4,
        child,
      ],
    );
  }
}
