package com.wincdspro.app.fragment.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.wincdspro.app.R
import com.wincdspro.app.constant.AppValues.Companion.APP_PACKAGE
import com.wincdspro.app.fragment.autotype.WincdsFragment

class MainMenuAboutFragment : WincdsFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_about, container, false)
    }

    private val versionName: String get() = requireActivity().packageManager.getPackageInfo(APP_PACKAGE, 0).versionName

    private val versionCode: Long
        @RequiresApi(Build.VERSION_CODES.P)
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                requireActivity().packageManager.getPackageInfo(APP_PACKAGE, 0).longVersionCode
            else
                requireActivity().packageManager.getPackageInfo(APP_PACKAGE, 0).versionCode.toLong()
        }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<TextView>(R.id.main_about_text_version).text = "App Version: $versionName ($versionCode)"
    }
}
