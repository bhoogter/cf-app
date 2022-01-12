package com.wincdspro.app.activity

import android.graphics.Bitmap
import android.graphics.Color.rgb
import android.net.wifi.WifiManager
import android.os.Build.DEVICE
import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import android.os.Bundle
import android.text.format.Formatter
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.wincdspro.app.R
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.exception.SettingsManagerException
import com.wincdspro.app.model.settings.sections.ServerSettings
import com.wincdspro.app.model.wincds.connect.ConnectionAssist
import com.wincdspro.app.model.wincds.connect.ConnectionAssistCollection
import com.wincdspro.app.util.CsvUtil.Companion.csvField
import com.wincdspro.app.util.ScannerUtil
import com.wincdspro.app.util.SettingsManager
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ServerSettingsActivity : AppCompatActivity() {
    private var capture: CaptureManager? = null
    private val defaultPort = 8080
    private var trialsAssist: List<ConnectionAssist> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        SettingsManager.load(this)
        serverSettings = SettingsManager.serverSettings
        fetchConnectionAssist()
    }

    override fun onStart() {
        super.onStart()
        try {
            capture = ScannerUtil.setupScanner(bcvScan, this)
        } catch (e: Exception) {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_LONG).show()
        }

        btnTest?.setOnClickListener { doTest() }
        btnScan?.setOnClickListener { doScan() }
        btnDetect?.setOnClickListener { doPortScan() }
        btnScan?.setOnClickListener { doScan() }
        btnCode?.setOnClickListener { showQrCode() }
    }

    override fun onStop() {
        super.onStop()
        saveConnectionAssist()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ScannerUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        bcvScan.resume()
    }

    override fun onPause() {
        super.onPause()
        bcvScan.pause()
        doSave()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture?.onDestroy()
        SettingsManager.load(this)
        doSave()
    }

    private val txtHost get() = findViewById<EditText>(R.id.settings_txt_host)
    private val txtPort get() = findViewById<EditText>(R.id.settings_txt_port)
    private val txtPass get() = findViewById<EditText>(R.id.settings_txt_pass)
    private val btnTest get() = findViewById<Button>(R.id.settings_button_test)
    private val btnDetect get() = findViewById<Button>(R.id.setting_button_detect)
    private val btnScan get() = findViewById<Button>(R.id.setting_button_scan)
    private val btnCode get() = findViewById<Button>(R.id.setting_button_code)
    private val imgCode get() = findViewById<ImageView>(R.id.setting_image_code)
    private val bcvScan get() = findViewById<DecoratedBarcodeView>(R.id.setting_barcode_scan)

    private var serverSettings: ServerSettings
        get() = ServerSettings(true, txtHost.text.toString(), txtPort.text.toString().toIntOrNull() ?: 0, txtPass.text.toString())
        set(u) {
            txtHost.setText(u.serverHost)
            txtPort.setText(u.serverPort.toString())
            txtPass.setText(u.serverPass.toString())
        }

    private fun doSave() {
        SettingsManager.settings.serversettings = serverSettings
        SettingsManager.save(this)
    }

    private fun doTest() {
        findViewById<Button>(R.id.settings_button_test).text = "Testing..."
        WincdsClient(txtHost.text.toString(), txtPort.text.toString().toIntOrNull() ?: 0, txtPass.text.toString())
            .connectionTest(this::settings_test_click_result)
    }

    private fun settings_test_click_result(status: Int, result: String?) {
        val display = if (status == SC_OK) "OK" else "FAIL"
        runOnUiThread {
            findViewById<Button>(R.id.settings_button_test).text = "Test - $display"
        }
    }

    private fun doScan() {
        if (capture == null) return
        if (!ScannerUtil.checkScanPermission(this)) return
        bcvScan.visibility = View.VISIBLE
        bcvScan.resume()
        bcvScan.decodeSingle(ScannerUtil.scannerCallback(::scanResult))
    }

    private fun scanResult(result: BarcodeResult) {
        bcvScan.pause()
        bcvScan.visibility = View.INVISIBLE
        txtHost.setText(csvField(result.text, 0))
        txtPort.setText(csvField(result.text, 1))
        txtPass.setText(csvField(result.text, 2))
        return
    }

    private fun generateQrCode(text: String): Bitmap {
        val width = 200
        val black = rgb(0, 0, 0)
        val white = rgb(255, 255, 255)

        val result: BitMatrix = try {
            MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, width, null)
        } catch (e: IllegalArgumentException) {
            throw SettingsManagerException("Could not build QR Code: ${e.message}")
        }
        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result[x, y]) black else white
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
        return bitmap // Find screen size
    }

    private fun showQrCode() {
        if (imgCode?.visibility == View.GONE) {
            imgCode?.visibility = View.VISIBLE
            bcvScan?.visibility = View.GONE
            val code = "${txtHost.text},${txtPort.text},${txtPass.text}"
            val img = generateQrCode(code)
            imgCode.background = img.toDrawable(resources)
        } else {
            imgCode?.visibility = View.GONE
        }
    }

    private val hostBase: String
        get() {
            val wm = getSystemService(WIFI_SERVICE) as WifiManager

            val ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
            val parts = ip.split(".")
            var res = parts.subList(0, 3).joinToString(".") + "."
            if (res == "0.0.0.") res = "192.168.0."
            return res
        }

    //    private fun getTrialsRange() = (1..254).toList()
//        .map { ip -> (8082..8082).toList().map { port -> Pair(ip, port) } }
//        .flatten()
    private fun getTrialsRange(): List<ConnectionAssist> = (1..254).toList().map { ConnectionAssist("$hostBase$it", (txtPort.text.toString().toIntOrNull() ?: 0).toString(), "") }
    private fun getTrialsAssist(): List<ConnectionAssist> = trialsAssist

    private fun getTrials(): List<ConnectionAssist> = getTrialsAssist() + getTrialsRange()

    private fun doPortScan() {
        scanPorts()
    }

    private fun scanPorts() {
        val es: ExecutorService = Executors.newFixedThreadPool(20)
        val timeout = 200
        val futures: MutableList<Future<Boolean>?> = ArrayList()
        val range = getTrials()
        range.forEach {
            futures.add(portIsOpen(es, it.host, it.port.toIntOrNull() ?: defaultPort, timeout))
        }
        es.shutdown()
        var openPorts = 0
        for (i in futures.indices)
            if (futures[i]?.get() == true) {
                txtHost.setText(range[i].host)
                openPorts++
            }
    }

    private fun portIsOpen(es: ExecutorService, ip: String?, port: Int, timeout: Int): Future<Boolean>? {
        return es.submit(
            Callable {
                try {
                    val socket = Socket()
                    socket.connect(InetSocketAddress(ip, port), timeout)
                    socket.close()
                    true
                } catch (ex: java.lang.Exception) {
                    false
                }
            }
        )
    }

    private fun saveConnectionAssist() {
        try {
            val name = "$MANUFACTURER ($MODEL) -  $DEVICE"
            WincdsClient().postConnectionAssist(name, txtHost.text.toString(), txtPort.text.toString(), txtPass.text.toString()) { _: Int, _: String? -> Unit }
        } catch (e: java.lang.Exception) {
            // We don't care about this...
        }
    }

    private fun fetchConnectionAssist() {
        WincdsClient().getConnectionAssist { status: Int, result: ConnectionAssistCollection? ->
            if (status == SC_OK && result != null) trialsAssist = result.results
        }
    }
}
