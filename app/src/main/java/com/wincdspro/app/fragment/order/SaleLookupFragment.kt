package com.wincdspro.app.fragment.order

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.client.WincdsSearchType
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_NEWSALE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_VIEWSALE
import com.wincdspro.app.exception.OrderEntryException
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.collection.SearchMailCollection
import com.wincdspro.app.model.wincds.collection.SearchSaleCollection
import com.wincdspro.app.model.wincds.search.SearchMail
import com.wincdspro.app.model.wincds.search.SearchSale
import com.wincdspro.app.util.DateUtil
import com.wincdspro.app.util.DateUtil.Companion.day
import com.wincdspro.app.util.DateUtil.Companion.month
import com.wincdspro.app.util.DateUtil.Companion.year
import com.wincdspro.app.util.SettingsManager
import com.wincdspro.app.util.TextWatcherImpl
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_OK
import java.util.Timer
import java.util.TimerTask

class SaleLookupFragment : WincdsFragment() {
    companion object {
        private var lookupTimer: Timer? = null
    }

    private val orderActivity get() = requireActivity() as OrderActivity
    private var mailIndex: Int
        get() = orderActivity.mailIndex
        set(v) {
            orderActivity.mailIndex = v
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_lookup, container, false)
    }

    override fun onStart() {
        super.onStart()
        txtInput?.addTextChangedListener(TextWatcherImpl(afterTextChanged = ::inputChanged))
        btnAllowNew?.setOnClickListener { orderActivity.receivedCust(0, this) }

        if (operation == ORDER_OPERATION_NEWSALE) {
            allowBySaleNo = false
            allowNew = true
            allowByDate = false
        } else {
            allowBySaleNo = true
            allowNew = false
            allowByDate = true
            lookupRecentSales()
        }

        optSale?.setOnClickListener { doRefresh() }
        optPhone?.setOnClickListener { doRefresh() }
        optName?.setOnClickListener { doRefresh() }
        btnDate?.setOnClickListener { datePickerVisible = !datePickerVisible; }
    }

    private val txtInput get() = activity?.findViewById<EditText>(R.id.order_lookup_input)
    private val optPhone get() = activity?.findViewById<RadioButton>(R.id.lookup_phone)
    private val optName get() = activity?.findViewById<RadioButton>(R.id.lookup_name)
    private val optSale get() = activity?.findViewById<RadioButton>(R.id.lookup_sale)
    private val layResults get() = activity?.findViewById<LinearLayout>(R.id.order_lookup_layout_results)
    private val lstResults get() = activity?.findViewById<ListView>(R.id.order_look_lst_results)
    private val layAllowNew get() = activity?.findViewById<LinearLayout>(R.id.order_lookup_layout_allownew)
    private val btnAllowNew get() = activity?.findViewById<Button>(R.id.order_lookup_button_allownew)
    private val btnDate get() = activity?.findViewById<Button>(R.id.lookup_date)
    private val dtpDate get() = activity?.findViewById<DatePicker>(R.id.lookup_datepicker_date)

    private var resultsVisible: Boolean
        get() = layResults?.visibility == View.VISIBLE
        set(v) {
            layResults?.visibility = if (v) View.VISIBLE else View.INVISIBLE
        }

    private var allowNew: Boolean
        get() = layResults?.visibility == View.VISIBLE
        set(v) {
            layAllowNew?.visibility = if (v) View.VISIBLE else View.INVISIBLE
        }

    private var allowBySaleNo: Boolean
        get() = optSale?.visibility == View.VISIBLE
        set(v) {
            if (!v && optSale?.isChecked == true) optPhone?.isChecked = true
            optSale?.visibility = if (v) View.VISIBLE else View.GONE
        }

    private var allowByDate
        get() = btnDate?.visibility == View.VISIBLE
        set(v) {
            btnDate?.visibility = if (v) View.VISIBLE else View.GONE
        }

    private var datePickerVisible
        get() = dtpDate?.visibility == View.VISIBLE
        set(v) {
            if (!allowByDate) return
            dtpDate?.visibility = if (v) View.VISIBLE else View.GONE
            if (v) dtpDate?.init(year(), month(), day()) { _: DatePicker, y: Int, m: Int, d: Int ->
                selectedDate("$y-${(m + 1).toString().padStart(2, '0')}-${d.toString().padStart(2, '0')}")
                datePickerVisible = false
            }
        }

    private fun selectedDate(d: String) {
        datePickerVisible = false
        client.searchSale(SettingsManager.storeNo, WincdsSearchType.DATE, d, ::inputChangedLookupSaleComplete)
    }

    private fun getSearchOption(): WincdsSearchType {
        return when {
            optName?.isChecked == true -> WincdsSearchType.NAME
            optSale?.isChecked == true -> WincdsSearchType.SALENO
            optPhone?.isChecked == true -> WincdsSearchType.PHONE
            else -> WincdsSearchType.PHONE
        }
    }

    private var input: String
        get() = txtInput?.text.toString()
        set(v) {
            txtInput?.setText(v)
        }

    private fun inputChanged(s: Editable) {
        inputChangedDispatch()
    }

    private fun lookupRecentSales() {
        client.searchSale(SettingsManager.storeNo, WincdsSearchType.DATE, DateUtil.formatDateUrl(), ::inputChangedLookupSaleComplete)
    }

    private fun doRefresh() = inputChangedDispatch(true)
    fun inputChangedDispatch(dispatch: Boolean = false) {
        if (!dispatch) {
            lookupTimer?.cancel()
            lookupTimer = Timer().apply {
                schedule(
                    object : TimerTask() {
                        override fun run() = inputChangedDispatch(true)
                    },
                    1000
                )
            }
        } else {
            lookupTimer = null
            activity?.runOnUiThread {
                if (input != "") when (operation) {
                    ORDER_OPERATION_VIEWSALE -> client.searchSale(SettingsManager.storeNo, getSearchOption(), input, ::inputChangedLookupSaleComplete)
                    ORDER_OPERATION_NEWSALE -> client.searchCustomer(SettingsManager.storeNo, getSearchOption(), input, ::inputChangedLookupCustComplete)
                }
            }
        }
    }

    private fun validateResult(status: Int, body: List<Any>?): Boolean {
        if (status != HTTP_OK && status != HTTP_NOT_FOUND) return !toast("Lookup failed [$status]")
        if (body == null) return !toast("No Results")
        return true
    }

    private fun attachListAction() {
        lstResults?.setOnItemClickListener { _: AdapterView<*>, view: View, _: Int, _: Long ->
            val ref = lineValue.invoke((view as TextView).text.toString())
            when (operation) {
                ORDER_OPERATION_VIEWSALE -> orderActivity.receivedSale(ref, this)
                ORDER_OPERATION_NEWSALE -> orderActivity.receivedCust(ref.toInt(), this)
            }
            false
        }
    }

    private fun inputChangedLookupSaleComplete(status: Int, body: SearchSaleCollection?) {
        resultsVisible = false
        if (!validateResult(status, body?.results)) return
        toast("Found results: ${body!!.results.size}")

        resultsVisible = true
        val lines = body.results.map(lineFunc)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_lookup_result, lines)
        lstResults?.adapter = adapter
        attachListAction()
    }

    private fun inputChangedLookupCustComplete(status: Int, body: SearchMailCollection?) {
        resultsVisible = false
        if (!validateResult(status, body?.results)) return
        toast("Found results: ${body!!.results.size}")

        resultsVisible = true
        val lines = body.results.map(lineFunc)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_lookup_result, lines)
        lstResults?.adapter = adapter
        attachListAction()
    }

    private fun saleLineFunc(it: SearchSale): String = "${(it.leaseNo).padEnd(15)}${it.last ?: "CASH & CARRY"}, ${it.first ?: ""} - ${it.sale} - ${it.tele ?: ""}"
    private fun saleLineValue(it: String): String = it.take(15).trim()
    private fun custLineFunc(it: SearchMail): String = "${it.index.toString().padEnd(10)} - ${it.last}, ${it.first}, ${it.tele}"
    private fun custLineValue(it: String): String = it.take(10).trim()

    private val lineFunc: (i: Any) -> String
        get() = when (operation) {
            ORDER_OPERATION_VIEWSALE -> { i: Any -> saleLineFunc(i as SearchSale) }
            ORDER_OPERATION_NEWSALE -> { i: Any -> custLineFunc(i as SearchMail) }
            else -> throw OrderEntryException("Unknown Operation: $operation")
        }
    private val lineValue
        get() = when (operation) {
            ORDER_OPERATION_VIEWSALE -> ::saleLineValue
            ORDER_OPERATION_NEWSALE -> ::custLineValue
            else -> throw OrderEntryException("Unknown Operation: $operation")
        }
}
