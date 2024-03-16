package com.example.scanner_app

import android.R.attr
import android.content.Intent
import android.os.Build
import android.util.Log
import com.journeyapps.barcodescanner.CaptureActivity
import io.flutter.embedding.android.FlutterActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import android.R.attr.data

import android.app.Activity





class MainActivity: FlutterActivity(){
    private val channelName = "androidUtils"
    private var methodChannelResult : MethodChannel.Result? = null

//    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
//        ScanContract()
//    ) { result: ScanIntentResult ->
//        if (result.contents == null) {
////            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
//        } else {
//            Log.d("BARCODERES","RES : ${result.contents}")
////            Toast.makeText(
////                this,
////                "Scanned: " + result.contents,
////                Toast.LENGTH_LONG
////            ).show()
//        }
//    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelName)
            .setMethodCallHandler { call, result ->
                methodChannelResult = result
                if (call.method.equals("getCurrentAndroidVersion")) {
                    result.success(getCurrentAndroidVersion())
                }
                if (call.method.equals("launchScanner")) {
                    launchScanner()
                    // result.success()
                }
            }
    }

    private fun getCurrentAndroidVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    private fun launchScanner(){
        val intent : Intent = Intent(this,BarcodeAndQrCodeReaderActivity::class.java)
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            val returnValue: String = data?.getStringExtra("result") ?: ""
            methodChannelResult?.success(returnValue)
        }
    }

}
