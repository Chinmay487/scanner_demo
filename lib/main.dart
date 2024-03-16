import 'dart:developer';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Demo',
      home:  MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

Future<String?> getBarcodeScanResults() async {
  if (!Platform.isAndroid) {
    return null;
  }

  const methodChannel = MethodChannel("androidUtils");

  return await methodChannel.invokeMethod<String>("launchScanner");
}


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: ElevatedButton(
          onPressed: () async {
            String? result = await getBarcodeScanResults();
            log(result ?? "");
          },
          child: const Text("PRESS BUTTON"),
        ),
      ),
    );
  }
}
