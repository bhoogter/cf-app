package com.wincdspro.app.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.activity.TrackingActivity
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.constant.PrivZones
import com.wincdspro.app.fragment.autotype.WincdsFragment

class MainMenuTrackingFragment : WincdsFragment() {
    private val mainActivity get() = (activity as MainActivity?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_tracking, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnPullOrder?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT_FACTORY_SHIPMENTS, TrackingActivity::class.java, OperationNames.TRK_FRAGMENT_PULLORDER) }
        btnReceivePo?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT_FACTORY_SHIPMENTS, TrackingActivity::class.java, OperationNames.TRK_FRAGMENT_RECPO) }
        btnStkLoc?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT_FACTORY_SHIPMENTS, TrackingActivity::class.java, OperationNames.TRK_FRAGMENT_STKLOC) }
    }

    private val btnPullOrder get() = activity?.findViewById<Button>(R.id.main_button_pullorder)
    private val btnReceivePo get() = activity?.findViewById<Button>(R.id.main_button_recpo)
    private val btnStkLoc get() = activity?.findViewById<Button>(R.id.main_button_stkloc)
}
