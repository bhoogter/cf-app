package com.wincdspro.app.fragment.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.constant.AppValues
import com.wincdspro.app.constant.AppValues.Companion.MAX_STORES
import com.wincdspro.app.constant.ArgNames.Companion.ARG_LOC
import com.wincdspro.app.constant.ArgNames.Companion.ARG_POSITION
import com.wincdspro.app.constant.ArgNames.Companion.ARG_QUAN
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STYLE
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.util.Format
import com.wincdspro.app.util.ScannerUtil
import com.wincdspro.app.util.SettingsManager
import java.util.Timer
import java.util.TimerTask

class ItemSelectFragment : WincdsFragment() {
    private var tmrType: Timer? = null

    private val maxQuan = MAX_STORES
    private val statusDefault = "ST"

    private val orderActivity get() = requireActivity() as OrderActivity
    private var position = 0

    private val loc get() = txtLoc?.text?.toString()?.toIntOrNull() ?: SettingsManager.storeNo
    private val quan get() = txtQuan?.text?.toString()?.toDouble() ?: 1.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_itemselect, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnScan?.setOnClickListener { scanStyle() }
        btnCancel?.setOnClickListener { doCancel() }
        btnSelect?.setOnClickListener { doSelect() }
        txtStyle?.addTextChangedListener { doChange() }

        position = arguments?.getInt(ARG_POSITION) ?: -1
        if (position >= 0) {
            txtStyle?.setText(arguments?.getString(ARG_STYLE))
            txtLoc?.setText("${arguments?.getInt(ARG_LOC) ?: 1}")
            txtQuan?.setText(Format.quantity(arguments?.getDouble(ARG_QUAN) ?: 1.0))
            spnStatus?.setSelection(0)
            loadStyle()
        }
    }

    private fun doSelect() {
        val style = txtStyle?.text.toString()
        val status = spnStatus?.selectedItem?.toString() ?: statusDefault
        val price = txtPrice?.text?.toString()?.toDoubleOrNull() ?: 0.0

        if (loc <= 0) {
            toast("Please enter a valid location.")
            return
        }

        if (quan <= 0) {
            toast("Quantity should be greater than zero.")
            return
        }

        val newItemFragment = orderActivity.popFragment(this) as SaleCreateFragment
        newItemFragment.receivedItemSelect(position, style, status, loc, quan, price)
    }

    override fun onPause() {
        super.onPause()
        if (scanVisible) scnStyle?.pause()
    }

    override fun onResume() {
        super.onResume()
        if (scanVisible) scnStyle?.resume()
    }

    private fun doCancel() {
        orderActivity.popFragment(this)
    }

    private val btnScan get() = activity?.findViewById<Button>(R.id.order_itemselect_button_scan)
    private val btnSelect get() = activity?.findViewById<Button>(R.id.order_itemselect_button_select)
    private val btnCancel get() = activity?.findViewById<Button>(R.id.order_itemselect_button_cancel)
    private val scnStyle get() = activity?.findViewById<DecoratedBarcodeView>(R.id.order_itemselect_scan_style)
    private val txtStyle get() = activity?.findViewById<EditText>(R.id.order_itemselect_text_style)
    private val txtLoc get() = activity?.findViewById<EditText>(R.id.order_itemselect_text_loc)
    private val txtQuan get() = activity?.findViewById<EditText>(R.id.order_itemselect_text_quan)
    private val txtDesc get() = activity?.findViewById<TextView>(R.id.order_itemselect_text_desc)
    private val txtPrice get() = activity?.findViewById<TextView>(R.id.order_itemselect_text_price)
    private val spnStatus get() = activity?.findViewById<Spinner>(R.id.order_itemselect_text_status)

    private fun fitStores(n: Int): Int = if (n < 1) 1 else (if (n > maxQuan) maxQuan else n)
    private fun txtAvail(n: Int) = activity?.findViewById<TextView>(resources.getIdentifier("order_itemselect_layout_avail${fitStores(n)}", "id", AppValues.APP_PACKAGE))
    private fun txtOrder(n: Int) = activity?.findViewById<TextView>(resources.getIdentifier("order_itemselect_layout_onorder${fitStores(n)}", "id", AppValues.APP_PACKAGE))

    private var scanVisible: Boolean
        get() = scnStyle?.visibility == View.VISIBLE
        set(v) {
            scnStyle?.visibility = if (v) View.VISIBLE else View.GONE
            txtStyle?.visibility = if (!v) View.VISIBLE else View.GONE
        }

    private fun scanStyle() {
        scanVisible = true
        scnStyle?.resume()
        scnStyle?.decodeSingle(ScannerUtil.scannerCallback(::scanResult))

//        IntentIntegrator.forSupportFragment(this).setOrientationLocked(false).initiateScan()
    }

    private fun scanResult(result: BarcodeResult) {
        scnStyle?.pause()
        txtStyle?.setText(result.text.toString())
        scanVisible = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                toast("Cancelled")
            } else {
                toast("Scanned: " + result.contents)
                txtStyle?.setText(result.contents)
                loadStyle()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun doChange(): Boolean {
        if (tmrType != null) {
            tmrType?.cancel()
            tmrType = null
        }
        populateData(Item()) // clear data

        tmrType = Timer()
        tmrType?.schedule(
            object : TimerTask() {
                override fun run() = loadStyle()
            },
            1000
        )
        return true
    }

    private fun loadStyle(style: String? = null) {
        val vStyle = style ?: txtStyle?.text?.toString() ?: ""
        WincdsClient.threaded {
            client.getItem(vStyle, ::loadStyleResponse)
        }
    }

    private fun loadStyleResponse(statusCode: Int, item: Item?) {
        if (statusCode == SC_OK && item != null) {
            populateData(item)
        } else {
            toast("Lookup failed: ${WincdsClient.lastError}")
        }
    }

    private fun populateData(item: Item) {
        txtDesc?.text = item.desc
        txtPrice?.text = Format.currency(item.onSale)
        populateQuantities(item)
    }

    private fun populateQuantities(item: Item) {
        for (i in 1..maxQuan) {
            txtAvail(i)?.text = Format.quantity(item.getLocBal(i))
            txtOrder(i)?.text = Format.quantity(item.getOnOrder(i))
            txtAvail(i)?.setOnLongClickListener { quickSetLoc(i) }
            txtOrder(i)?.setOnLongClickListener { quickSetLoc(i) }
        }
    }

    private fun quickSetLoc(i: Int): Boolean {
        txtLoc?.setText(i.toString())
        return true
    }
}
