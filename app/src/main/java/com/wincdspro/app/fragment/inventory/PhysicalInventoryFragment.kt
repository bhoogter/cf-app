package com.wincdspro.app.fragment.inventory

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.wincdspro.app.R
import com.wincdspro.app.data.PhysicalInventoryData
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.request.PhysicalInventoryResponse
import com.wincdspro.app.util.ScannerUtil

class PhysicalInventoryFragment : WincdsFragment() {
    private lateinit var capture: CaptureManager
    var isFlashOn = false

    private var savedInstance: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        savedInstance = savedInstanceState
        return inflater.inflate(R.layout.fragment_inventory_physicalinv, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnScan?.setOnClickListener { doScan() }
        btnFinish?.setOnClickListener { doFinish() }
        btnClear?.setOnClickListener { doClear() }

        btnScanner?.setOnClickListener { doScanner() }
        btnLight?.visibility = if (hasFlash()) View.VISIBLE else View.GONE
        btnLight?.setOnClickListener { doFlash(!isFlashOn) }
        btnEnter?.setOnClickListener { doEnter() }

        capture = ScannerUtil.setupScanner(scanner!!, requireActivity(), savedInstance)
        updateStats()
    }

    private fun hasFlash(): Boolean = requireActivity().applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    private fun doFlash(newValue: Boolean) {
        isFlashOn = newValue
        if (isFlashOn) scanner?.setTorchOn() else scanner?.setTorchOff()
    }

    private fun doScanner() {
        toast("Scanning...")
        scanner?.resume()
        scanner?.decodeSingle(ScannerUtil.scannerCallback(::barcodeResult))
    }

    private fun barcodeResult(result: BarcodeResult) {
        recordStyle(result.text)
        toast("RESULT: ${result.text}")
        scanner?.pause()
        scanner?.clearAnimation()
    }

    override fun onResume() {
        super.onResume()
        scanner?.resume()
    }

    override fun onPause() {
        super.onPause()
        scanner?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    private val btnLight get() = activity?.findViewById<Button>(R.id.inventory_phyinv_light)
    private val scanner get() = activity?.findViewById<DecoratedBarcodeView>(R.id.inventory_phyinv_scanner)
    private val btnScanner get() = activity?.findViewById<Button>(R.id.inventory_phyinv_btn_scanner)
    private val txtInput get() = activity?.findViewById<EditText>(R.id.inventory_phyinv_input)
    private val btnEnter get() = activity?.findViewById<Button>(R.id.inventory_phyinv_enter)
    private val btnScan get() = activity?.findViewById<Button>(R.id.inventory_phyinv_scan)
    private val btnFinish get() = activity?.findViewById<Button>(R.id.inventory_phyinv_finish)
    private val btnClear get() = activity?.findViewById<Button>(R.id.inventory_phyinv_clear)

    private val txtTotalScanned get() = activity?.findViewById<TextView>(R.id.inv_phyinv_txt_scantotal)
    private val txtLastStyle get() = activity?.findViewById<TextView>(R.id.inv_phyinv_txt_laststyle)
    private val txtUniqueStyles get() = activity?.findViewById<TextView>(R.id.inv_phyinv_txt_uniquestyles)

    private var input: String
        get() = txtInput?.text.toString()
        set(value) = txtInput!!.setText(value)

    private fun recordStyle(style: String) {
        if (style.isEmpty()) return
        PhysicalInventoryData.recordStyle(requireContext(), style)
        updateStats()
    }

    private fun doEnter(): Boolean {
        recordStyle(input)
        txtInput?.selectAll()
        return true
    }

    private fun doClear() {
        PhysicalInventoryData.clearStyles(requireContext())
        updateStats()
    }

    private fun doFinish() {
        val report = PhysicalInventoryData.generateReport(requireContext())
        client.postPhysicalInventoryReport(report, ::finishResult)
    }

    private fun finishResult(statusCode: Int, physicalInventoryResponse: PhysicalInventoryResponse?) {
        toast("Response: $statusCode: ${physicalInventoryResponse?.response ?: ""}")
    }

    private fun updateStats() {
        txtLastStyle?.text = PhysicalInventoryData.getLastStyle(requireContext())
        txtTotalScanned?.text = PhysicalInventoryData.totalItems(requireContext()).toString()
        txtUniqueStyles?.text = PhysicalInventoryData.totalStyles(requireContext()).toString()
    }

    private fun doScan(): Unit = if (ScannerUtil.checkScanPermission(requireActivity(), ::beginScan)) beginScan() else Unit
    private fun beginScan() = IntentIntegrator.forSupportFragment(this).setOrientationLocked(false).initiateScan()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ScannerUtil.onRequestPermissionsResult(requireActivity(), requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                toast("Cancelled")
            } else {
                toast("Scanned: " + result.contents)
                recordStyle(result.contents)
                beginScan()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
