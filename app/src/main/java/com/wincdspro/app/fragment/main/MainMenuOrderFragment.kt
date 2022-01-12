package com.wincdspro.app.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.activity.ReportActivity
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_NEWSALE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_VIEWSALE
import com.wincdspro.app.constant.PrivZones
import com.wincdspro.app.fragment.autotype.WincdsFragment

class MainMenuOrderFragment : WincdsFragment() {
    private val mainActivity get() = (activity as MainActivity?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_order, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnViewSale?.setOnClickListener { mainActivity?.launchOperation(PrivZones.CUSTOMER_MANAGEMENT_VIEW_SALES, OrderActivity::class.java, ORDER_OPERATION_VIEWSALE) }
        btnNewSale?.setOnClickListener { mainActivity?.launchOperation(PrivZones.CUSTOMER_MANAGEMENT_CREATE_SALES, OrderActivity::class.java, ORDER_OPERATION_NEWSALE) }
        btnDailyAudit?.setOnClickListener { mainActivity?.launchOperation(PrivZones.FINANCIAL_MANAGEMENT_DAILY_AUDIT_REPORT, ReportActivity::class.java, OperationNames.RPT_OPERATION_DAILYAUDIT) }
    }

    private val btnViewSale get() = activity?.findViewById<Button>(R.id.main_button_viewsale)
    private val btnNewSale get() = activity?.findViewById<Button>(R.id.main_button_newsale)
    private val btnDailyAudit get() = activity?.findViewById<Button>(R.id.main_button_dailyAudit)
}
