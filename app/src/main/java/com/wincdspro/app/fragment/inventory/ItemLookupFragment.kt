package com.wincdspro.app.fragment.inventory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.wincdspro.app.R
import com.wincdspro.app.activity.InventoryActivity
import com.wincdspro.app.client.WincdsSearchType
import com.wincdspro.app.fragment.autotype.LookupFragment
import com.wincdspro.app.model.wincds.collection.SearchItemCollection
import com.wincdspro.app.model.wincds.search.SearchItem

class ItemLookupFragment : LookupFragment<SearchItemCollection, SearchItem, String>() {
    private val MY_CAMERA_REQUEST_CODE = 100
    private val inventoryActivity get() = (requireActivity() as InventoryActivity)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventory_item_lookup, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnScan?.setOnClickListener { doScan() }
        optStyle?.setOnClickListener { refreshLookup() }
        optDesc?.setOnClickListener { refreshLookup() }
        optVendor?.setOnClickListener { refreshLookup() }
    }

    private val optStyle get() = activity?.findViewById<RadioButton>(R.id.lookup_style)
    private val optDesc get() = activity?.findViewById<RadioButton>(R.id.lookup_desc)
    private val optVendor get() = activity?.findViewById<RadioButton>(R.id.lookup_vendor)
    private val btnScan get() = activity?.findViewById<Button>(R.id.inventory_lookup_scan)

    override fun getSearchOption(): WincdsSearchType {
        return when {
            optStyle?.isChecked == true -> WincdsSearchType.STYLE
            optDesc?.isChecked == true -> WincdsSearchType.DESC
            optVendor?.isChecked == true -> WincdsSearchType.VENDOR
            else -> WincdsSearchType.STYLE
        }
    }

    override fun idTxtInput(): Int = R.id.inventory_lookup_input
    override fun lineFunc(i: SearchItem): String = "${i.style.padEnd(20)} - ${i.desc}"
    override fun lineValue(i: String): String = i.take(20).trim()
    override fun performLookup(searchType: WincdsSearchType, input: String, result: (status: Int, body: SearchItemCollection?) -> Unit) = client.searchItem(getSearchOption(), input, result)
    override fun mapObjectToLines(c: SearchItemCollection): List<String> = c.results.map(::lineFunc)
    override fun selected(item: String, f: Fragment) = inventoryActivity.receivedItemStyle(item, f)

    private fun doScan(): Unit? = if (checkScanPermission()) beginScan() else null

    private fun checkScanPermission(): Boolean {
        return if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_REQUEST_CODE)
            false
        } else true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("Camera Permission Granted")
                beginScan()
            } else {
                toast("Camera Permission Denied.  Cannot Scan Barcodes")
            }
        }
    }

    private fun beginScan() = IntentIntegrator.forSupportFragment(this).setOrientationLocked(false).initiateScan()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                toast("Cancelled")
            } else {
                toast("Scanned: " + result.contents)
                txtInput?.setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
