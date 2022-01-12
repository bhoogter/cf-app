package com.wincdspro.app.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlin.reflect.KFunction1

class ScannerUtil {
    companion object {
        private const val AUTOFOCUS_FEATURE = "android.hardware.camera.autofocus"
        private const val MY_CAMERA_REQUEST_CODE: Int = 100
        private var afterCheckPermissionOperation: (() -> Unit)? = null

        @JvmStatic
        fun checkScanPermission(activity: Activity, operation: (() -> Unit)? = null): Boolean {
            afterCheckPermissionOperation = operation
            return if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_REQUEST_CODE)
                false
            } else true
        }

        @JvmStatic
        @Suppress("UNUSED_ARGS")
        fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
            if (requestCode == MY_CAMERA_REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "Camera Permission Granted", Toast.LENGTH_LONG).show()
                    afterCheckPermissionOperation?.invoke()
                } else {
                    Toast.makeText(activity, "Camera Permission Denied.  Cannot Scan Barcodes", Toast.LENGTH_LONG).show()
                }
            }
        }

        @JvmStatic
        fun cameraHasAutoFocus(activity: Activity): Boolean = activity.packageManager.hasSystemFeature(AUTOFOCUS_FEATURE)

        @JvmStatic
        fun setupScanner(scanner: DecoratedBarcodeView, activity: Activity, bundle: Bundle? = null): CaptureManager {
            scanner.initializeFromIntent(activity.intent)
            if (cameraHasAutoFocus(activity)) scanner.cameraSettings.isAutoFocusEnabled = true
            scanner.cameraSettings.isExposureEnabled = true
            scanner.cameraSettings.isMeteringEnabled = true
            scanner.setStatusText("Align barcode and press 'Scan'.")
            scanner.pause()

            val capture = CaptureManager(activity, scanner)
            capture.initializeFromIntent(activity.intent, bundle)
            return capture
        }

        @JvmStatic
        fun scannerCallback(callback: KFunction1<BarcodeResult, Unit>): BarcodeCallback {
            return object : BarcodeCallback {
                override fun barcodeResult(result: BarcodeResult) {
                    callback.invoke(result)
                }

                override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
            }
        }
    }
}
