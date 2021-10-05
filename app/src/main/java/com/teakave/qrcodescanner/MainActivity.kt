package com.teakave.qrcodescanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.teakave.qrcodescanner.databinding.ActivityMainBinding

private const val CAMERA_PERMISSION_REQUEST_CODE = 5954

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val openQrCodeScanner = registerForActivityResult(QrCodeScannerContract()) { result ->
        Toast.makeText(this, result ?: "NULL", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onQrScannerClick(view: View) {
        if (hasCameraPermission()) openQrCodeScanner()
        else requestPermission()
    }

    private fun openQrCodeScanner() {
        openQrCodeScanner.launch(1)
    }

    // checking to see whether user has already granted permission
    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        // opening up dialog to ask for camera permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // user granted permissions - we can set up our scanner
            openQrCodeScanner()
        } else {
            // user did not grant permissions - we can't use the camera
            Toast.makeText(
                this,
                "Camera permission required",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
