package com.wincdspro.app.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.util.WLogger

class DebugFragment : WincdsFragment() {
    private val mainActivity get() = (requireActivity() as MainActivity)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_debug, container, false)
    }

    private val txtInput get() = activity?.findViewById<TextView>(R.id.main_debug_text_log)

    override fun onStart() {
        super.onStart()

        refresh()
        txtInput?.setOnLongClickListener { WLogger.clear(); refresh() }
    }

    private fun refresh(): Boolean {
        txtInput?.text = WLogger.getLogs().reversed().joinToString("\n")
        return true
    }
}
