package com.wincdspro.app.fragment.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.wincdspro.app.R
import com.wincdspro.app.adapter.BosLineAdapter
import com.wincdspro.app.adapter.BosLineModel
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.constant.ArgNames.Companion.ARG_SALENO
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.collection.SaleNoCollection
import com.wincdspro.app.model.wincds.core.Sale
import com.wincdspro.app.util.Format
import com.wincdspro.app.util.SettingsManager
import java.net.HttpURLConnection.HTTP_OK

class SaleViewFragment : WincdsFragment() {
    private var saleNo: String = ""
    private var saleList: SaleNoCollection? = null

    private val storeNo: Int get() = SettingsManager.storeNo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_viewsale, container, false)
    }

    override fun onStart() {
        super.onStart()
        saleNo = arguments?.getString(ARG_SALENO) ?: ""
        fetchSale(storeNo, saleNo)

        lstItems?.addHeaderView(getHeader())
        lstItems?.addFooterView(getFooter())
        btnPrev?.setOnClickListener { navigate(-1) }
        btnNext?.setOnClickListener { navigate(1) }

        client.getSaleNoList(SettingsManager.storeNo) { statusCode: Int, sales: SaleNoCollection? -> if (statusCode == SC_OK && sales != null) saleList = sales }
    }

    private fun getFooter() = (requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.row_viewsale_footer, null, false)
    private fun getHeader() = (requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.row_viewsale_header, null, false)

    private val txtSaleNo get() = requireActivity().findViewById<TextView>(R.id.order_viewsale_text_saleno)
    private val txtSaleDate get() = requireActivity().findViewById<TextView>(R.id.order_viewsale_text_saledate)
    private val txtCust get() = requireActivity().findViewById<TextView>(R.id.order_viewsale_text_cust)
    private val txtBalDue get() = requireActivity().findViewById<TextView>(R.id.row_viewsale_footer_price)
    private val lstItems get() = requireActivity().findViewById<ListView>(R.id.viewsale_list_items)
    private val btnPrev get() = requireActivity().findViewById<Button>(R.id.order_viewsale_button_prev)
    private val btnNext get() = requireActivity().findViewById<Button>(R.id.order_viewsale_button_next)

    private fun fetchSale(storeNo: Int, saleNo: String) {
        this.saleNo = saleNo
        client.getSale(storeNo, saleNo, ::fetchSaleComplete)
    }

    private fun fetchSaleComplete(status: Int, sale: Sale?) {
        if (status == HTTP_OK && sale != null) {
            txtSaleNo.text = sale.leaseNo
            txtSaleDate.text = Format.date(sale.items?.get(0)?.sellDate ?: "")
            txtCust.text = "${sale.mail?.last}, ${sale.mail?.first}"
            txtBalDue?.text = Format.currency(sale.sale - sale.deposit)

            val lines: ArrayList<BosLineModel> = ArrayList(sale.items?.map(BosLineModel::of) ?: listOf())
            val adapter = BosLineAdapter(lines, requireContext())
            lstItems.adapter = adapter
        }
    }

    private fun navigate(dir: Int) {
        if (saleList != null) {
            val index = (saleList!!.results.indexOf(txtSaleNo.text.toString()) + (dir / kotlin.math.abs(dir))).coerceIn(saleList!!.results.indices)
            saleNo = saleList!!.results[index]
            fetchSale(SettingsManager.storeNo, saleNo)
        }
    }
}
