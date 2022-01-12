package com.wincdspro.app.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wincdspro.app.constant.ArgNames.Companion.ARG_RESULT_ID
import com.wincdspro.app.constant.OperationNames.Companion.RPT_FRAGMENT_DAILYAUDIT
import com.wincdspro.app.constant.OperationNames.Companion.RPT_FRAGMENT_RESULTS
import com.wincdspro.app.constant.OperationNames.Companion.RPT_FRAGMENT_VIEW
import com.wincdspro.app.constant.OperationNames.Companion.RPT_OPERATION_DAILYAUDIT
import com.wincdspro.app.constant.OperationNames.Companion.RPT_OPERATION_RESULTS
import com.wincdspro.app.constant.OperationNames.Companion.RPT_OPERATION_VIEW
import com.wincdspro.app.fragment.order.DailyAuditFragment
import com.wincdspro.app.fragment.support.ReportResultsFragment
import com.wincdspro.app.fragment.support.ReportViewFragment

class ReportActivity : FragmentActivity() {

    override fun initialFragment(operation: String): String = when (operation) {
        RPT_OPERATION_DAILYAUDIT -> RPT_FRAGMENT_DAILYAUDIT
        RPT_OPERATION_RESULTS -> RPT_FRAGMENT_RESULTS
        RPT_OPERATION_VIEW -> RPT_FRAGMENT_VIEW
        else -> super.initialFragment(operation)
    }

    override fun lookupFragment(fragmentName: String): Fragment = when (fragmentName) {
        RPT_FRAGMENT_RESULTS -> ReportResultsFragment()
        RPT_FRAGMENT_VIEW -> ReportViewFragment()
        RPT_FRAGMENT_DAILYAUDIT -> DailyAuditFragment()
        else -> super.lookupFragment(fragmentName)
    }

    fun loadReport(id: String, previous: Fragment? = null) {
        val args = Bundle()
        args.putString(ARG_RESULT_ID, id)
        setFragment(RPT_FRAGMENT_VIEW, args, previous)
    }
}
