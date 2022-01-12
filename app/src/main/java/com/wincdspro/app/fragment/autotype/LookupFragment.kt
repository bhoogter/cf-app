package com.wincdspro.app.fragment.autotype

import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wincdspro.app.R
import com.wincdspro.app.client.HttpStatusCode
import com.wincdspro.app.client.WincdsSearchType
import com.wincdspro.app.util.TextWatcherImpl
import java.util.Timer
import java.util.TimerTask

abstract class LookupFragment<C, T, V> : WincdsFragment() {
    private var lookupTimer: Timer? = null

    open fun lookupDelay() = 1000L
    open fun idTxtInput(): Int = R.id.inventory_lookup_results
    open fun idLayResults(): Int = R.id.inventory_lookup_results
    open fun idLstResults(): Int = R.id.inventory_lookup_lst_results
    abstract fun getSearchOption(): WincdsSearchType
    abstract fun performLookup(searchType: WincdsSearchType, input: String, result: (status: Int, body: C?) -> Unit)
    abstract fun mapObjectToLines(c: C): List<String>
    open fun lookupFailed() = Unit
    abstract fun lineFunc(i: T): String
    abstract fun lineValue(s: String): V
    abstract fun selected(item: V, f: Fragment)

    override fun onStart() {
        super.onStart()
        txtInput?.addTextChangedListener(TextWatcherImpl(afterTextChanged = ::inputChanged))
    }

    protected open val txtInput get() = activity?.findViewById<EditText>(idTxtInput())
    protected open val layResults get() = activity?.findViewById<LinearLayout>(idLayResults())
    protected open val lstResults get() = activity?.findViewById<ListView>(idLstResults())

    protected open var resultsVisible: Boolean
        get() = layResults?.visibility == View.VISIBLE
        set(v) {
            layResults?.visibility = if (v) View.VISIBLE else View.INVISIBLE
        }

    protected open var input: String
        get() = txtInput?.text.toString()
        set(value) {
            txtInput?.setText(value)
        }

    protected open fun refreshLookup() = inputChangedDispatch()
    protected open fun inputChanged(s: Editable) = inputChangedDispatch()
    protected open fun inputChangedDispatch(dispatch: Boolean = false) {
        if (!dispatch) {
            lookupTimer?.cancel()
            lookupTimer = Timer().apply {
                schedule(
                    object : TimerTask() {
                        override fun run() = inputChangedDispatch(true)
                    },
                    lookupDelay()
                )
            }
        } else {
            lookupTimer = null
            performLookup(getSearchOption(), input, ::inputChangedLookupItemComplete)
        }
    }

    protected open fun inputChangedLookupItemComplete(status: Int, body: C?) {
        resultsVisible = false
        if (status != HttpStatusCode.SC_OK || body == null) {
            lookupFailed()
        } else {
            resultsVisible = true
            val lines = mapObjectToLines(body)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_lookup_result, lines)
            lstResults?.adapter = adapter
            attachListAction()
        }
    }

    protected open fun attachListAction() {
        lstResults?.setOnItemClickListener { _: AdapterView<*>, view: View, _: Int, _: Long ->
            selected(lineValue((view as TextView).text.toString()), this)
        }
    }
}
