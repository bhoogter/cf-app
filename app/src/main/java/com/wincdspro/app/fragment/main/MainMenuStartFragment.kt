package com.wincdspro.app.fragment.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.activity.ReportActivity
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.util.SettingsManager
import java.io.InputStream
import java.util.Timer
import java.util.TimerTask

class MainMenuStartFragment : WincdsFragment() {
    private var tmrStatus: Timer? = null
    private val noImage = R.drawable.noprofile2
    private var logoImage: Int = 0

    private val scanSchedulePeriod = 1_000L

    private val mainActivity get() = (activity as MainActivity?)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_start, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnOrderMenu?.setOnClickListener { mainActivity?.setSubMenu(OperationNames.MAIN_MENU_ORDER) }
        btnInventoryMenu?.setOnClickListener { mainActivity?.setSubMenu(OperationNames.MAIN_MENU_INVENT) }
        btnTrackingMenu?.setOnClickListener { mainActivity?.setSubMenu(OperationNames.MAIN_MENU_TRACKG) }
        btnReportsMenu?.setOnClickListener { mainActivity?.launchOperation(null, ReportActivity::class.java, OperationNames.RPT_OPERATION_RESULTS) }
        imgProfile?.setOnLongClickListener {
            SettingsManager.load(mainActivity)
            true
        }
    }

    override fun onPause() {
        super.onPause()
        doTimer()
    }

    override fun onResume() {
        super.onResume()
        doTimer(true)
    }

    private val imgProfile get() = activity?.findViewById<ImageView>(R.id.main_start_image_profile)
    private val btnOrderMenu get() = activity?.findViewById<Button>(R.id.main_button_orderentry)
    private val btnInventoryMenu get() = activity?.findViewById<Button>(R.id.main_button_inventory)
    private val btnTrackingMenu get() = activity?.findViewById<Button>(R.id.main_button_tracking)
    private val btnReportsMenu get() = activity?.findViewById<Button>(R.id.main_button_reports)
    private val txtStatus get() = activity?.findViewById<TextView>(R.id.main_start_text_status)

    private fun doTimer(on: Boolean = false) {
        if (!on) {
            if (tmrStatus == null) return
            tmrStatus?.cancel()
        } else {
            if (tmrStatus != null) doTimer()
            tmrStatus = Timer()
            val task = object : TimerTask() {
                override fun run() = requireActivity().runOnUiThread {
                    updateStatus()
                    updateImage()
                }
            }
            tmrStatus?.schedule(task, 0, scanSchedulePeriod)
        }
    }

    private fun updateStatus() {
        val sNo = SettingsManager.storeNo
        val info = SettingsManager.getStoreInfo(sNo).storeInfo
        val user = SettingsManager.loggedInUser ?: "[No User]"
        val groups = when {
            SettingsManager.isAdmin -> " [Admin]"
            SettingsManager.settings.connectionSettings.authToken.groups.isNotEmpty() -> " [${SettingsManager.settings.connectionSettings.authToken.groups.joinToString("")}]"
            else -> ""
        }

        var str: String
        if (!SettingsManager.loggedIn || info.name == null) str = if (SettingsManager.loggingIn) "Connecting..." else "Not Connected"
        else {
            str = "L$sNo: ${info.name}"
            if (!info.address.isNullOrBlank()) str += "\n${info.address}"
            str += "\n$user$groups"
        }
        txtStatus?.text = str
    }

    private fun updateImage() {
        if (!SettingsManager.loggedIn) {
            imgProfile?.setImageResource(noImage)
            logoImage = 0
        } else if (logoImage != SettingsManager.storeNo) {
            imgProfile?.setImageResource(noImage)
            logoImage = SettingsManager.storeNo
            fetchStoreLogo()
        }
    }

    private fun fetchStoreLogo() {
        WincdsClient().getStoreLogo(SettingsManager.storeNo) { status: Int, stream: InputStream? ->
            if (status == SC_OK && stream != null) {
                imgProfile?.setImageResource(noImage)
                imgProfile?.setImageBitmap(BitmapFactory.decodeStream(stream))
            } else imgProfile?.setImageResource(noImage)
        }
    }
}
