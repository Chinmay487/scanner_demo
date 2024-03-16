package com.example.scanner_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class BarcodeAndQrCodeReaderActivity : AppCompatActivity() {

        private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
            handleScanResults(result = result)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_barcode_and_qr_code_reader)
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES) // CHANGE THIS TO SCAN DIFFERENT TYPES OF Barcodes
        options.setPrompt("Scan a barcode")
        options.setCameraId(0) // Use a specific camera of the device

        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }

    private fun handleScanResults(result: ScanIntentResult){
        val intent : Intent = Intent()
        intent.putExtra("result", result.contents ?: "" );
        setResult(RESULT_OK,intent)
        finish()
    }

}