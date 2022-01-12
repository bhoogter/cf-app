package com.wincdspro.app.fragment.main

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.constant.ArgNames.Companion.ARG_TYPE
import com.wincdspro.app.fragment.autotype.WincdsFragment

class AppHelpFragment : WincdsFragment() {
    private val mainActivity get() = (requireActivity() as MainActivity)

    enum class PermissionRequestType {
        CAMERA
    }

    val type: PermissionRequestType get() = PermissionRequestType.valueOf(arguments?.getString(ARG_TYPE) ?: PermissionRequestType.CAMERA.toString())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_apphelp, container, false)
    }

    override fun onStart() {
        super.onStart()
        txtUrl?.movementMethod = LinkMovementMethod.getInstance()
    }

    private val txtUrl get() = activity?.findViewById<TextView>(R.id.main_help_url)
}
