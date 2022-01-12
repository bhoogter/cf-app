package com.wincdspro.app.fragment.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.wincdspro.app.R
import com.wincdspro.app.activity.InventoryActivity
import com.wincdspro.app.constant.AppValues.Companion.MAX_STYLE
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STYLE
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.model.wincds.core.ItemDetail
import com.wincdspro.app.model.wincds.core.collection.ItemDetailCollection
import java.net.HttpURLConnection.HTTP_OK

class ItemViewDetailFragment : WincdsFragment() {
    private val inventoryActivity get() = (requireActivity() as InventoryActivity)
    private val style: String get() = arguments?.getString(ARG_STYLE) ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventory_item_viewdetail, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnClose?.setOnClickListener { inventoryActivity.popFragment(this) }
        getItemInfo()
        getItemDetail()
    }

    private val btnClose get() = activity?.findViewById<TextView>(R.id.inv_viewdetail_close)
    private val txtStyle get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_style)
    private val txtVendor get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_vendor)
    private val txtVendorNo get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_vendorno)
    private val txtDesc get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_desc)
    private val lstDetail get() = activity?.findViewById<ListView>(R.id.inv_viewdetail_list)

    private fun getItemInfo() = client.getItem(style, ::itemInfoReceived)
    private fun getItemDetail() = client.getItemDetail(style, ::itemDetailReceived)

    private fun itemInfoReceived(status: Int, item: Item?) {
        if (HTTP_OK != status || item == null) {
            toast("Failed fetching item: $status")
            return
        }
        populateItem(item)
    }

    private fun populateItem(item: Item) {
        txtStyle?.text = item.style
        txtVendor?.text = item.vendor
        txtVendorNo?.text = item.vendorNo
        txtDesc?.text = item.desc
    }

    private fun itemDetailReceived(status: Int, detail: ItemDetailCollection?) {
        if (HTTP_OK != status || detail == null) {
            toast("Failed fetching item: $status")
            return
        }
        populateItemDetails(detail)
    }

    private fun populateItemDetails(items: ItemDetailCollection) {
        val lines = mapObjectToLines(items)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_itemviewdetail_result, lines)
        lstDetail?.adapter = adapter
    }

    private fun mapObjectToLines(items: ItemDetailCollection): List<String> = items.results.map(::lineString)
    private fun lineString(item: ItemDetail): String {
        var s = ""
        s += (item.ddate1 ?: "").padEnd(20)
        s += item.style.padEnd(MAX_STYLE + 5)
        s += (item.trans ?: "").padEnd(15)
        s += item.saleNo.padEnd(20)
        return s
    }
}
