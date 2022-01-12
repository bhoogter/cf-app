package com.wincdspro.app.fragment.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.wincdspro.app.R
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.constant.ArgNames.Companion.ARG_PONO
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.collection.PoNoCollection
import com.wincdspro.app.model.wincds.core.Po
import com.wincdspro.app.model.wincds.core.PoItem
import com.wincdspro.app.util.Format
import com.wincdspro.app.util.SettingsManager

class PoViewFragment : WincdsFragment() {
    private var poNo: Int = 0
    private var poList: PoNoCollection? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventory_po_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadPoNo(arguments?.getInt(ARG_PONO) ?: 0)

        btnPrev?.setOnClickListener { navigate(-1) }
        btnNext?.setOnClickListener { navigate(1) }

        client.getPoNoList { statusCode: Int, sales: PoNoCollection? -> if (statusCode == SC_OK && sales != null) poList = sales }
    }

    private val txtPoNo get() = activity?.findViewById<TextView>(R.id.inventory_po_text_pono)
    private val txtLoc get() = activity?.findViewById<TextView>(R.id.inventory_po_text_loc)
    private val txtOrderNo get() = activity?.findViewById<TextView>(R.id.inventory_po_text_orderno)
    private val txtTag get() = activity?.findViewById<TextView>(R.id.inventory_po_text_tag)
    private val txtVendor get() = activity?.findViewById<TextView>(R.id.inventory_po_text_vendor)
    private val txtPoDate get() = activity?.findViewById<TextView>(R.id.inventory_po_text_date)
    private val txtPoCost get() = activity?.findViewById<TextView>(R.id.inventory_po_text_date)

    private val btnPrev get() = activity?.findViewById<TextView>(R.id.order_po_button_prev)
    private val btnNext get() = activity?.findViewById<TextView>(R.id.order_po_button_next)

    private val txtSoldTo get() = activity?.findViewById<TextView>(R.id.inventory_po_text_soldto)
    private val txtShipTo get() = activity?.findViewById<TextView>(R.id.inventory_po_text_shipto)
    private val txtSpecial get() = activity?.findViewById<TextView>(R.id.inventory_po_text_special)

    private val lstItems get() = activity?.findViewById<ListView>(R.id.inventory_po_list_items)

    private fun loadPoNo(poNo: Int = this.poNo) = client.getPoByPoNo(poNo) { status: Int, po: Po? ->
        this.poNo = poNo
        if (status == SC_OK && po != null) {
            txtPoNo?.text = po.poNo
            txtLoc?.text = po.items[0].location.toString()
            txtOrderNo?.text = ""
            txtTag?.text = ""
            txtVendor?.text = po.vendors[0]
            txtPoDate?.text = po.poDates[0]

            txtSoldTo?.text = SettingsManager.getStoreInfo(po.items[0].soldTo ?: SettingsManager.storeNo).storeInfo.name
            txtShipTo?.text = SettingsManager.getStoreInfo(po.items[0].shipTo ?: SettingsManager.storeNo).storeInfo.name

            loadItems(po.items)
        }
    }

    private fun line(i: PoItem) = "${i.style?.padEnd(20)}${Format.quantity(i.quantity ?: 0.0).padEnd(10)}${Format.currency(i.cost ?: 0.0)}   - ${i.printPO} - ${i.poRecDate ?: ""} - ${i.ackInv ?: ""}"

    private fun loadItems(items: List<PoItem>) {
        val lines = items.map(::line)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_lookup_result, lines)
        lstItems?.adapter = adapter
    }

    private fun navigate(dir: Int) {
        if (poList != null) {
            val index = (poList!!.results.indexOf(txtPoNo?.text.toString().toIntOrNull() ?: 0) + (dir / kotlin.math.abs(dir))).coerceIn(poList!!.results.indices)
            loadPoNo(poList!!.results[index])
        }
    }
}
