package com.teakave.qrcodescanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class QrCodeScannerContract : ActivityResultContract<Int, String?>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, QrCodeScannerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra(QrCodeScannerActivity.RESULT_ID)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }

}
