package com.wincdspro.app.client

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wincdspro.app.activity.ReportActivity
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.constant.ReportIds.Companion.REPORT_DAILY_AUDIT
import com.wincdspro.app.data.ReportData
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import com.wincdspro.app.util.DateUtil.Companion.formatDateUrl
import com.wincdspro.app.util.Format
import com.wincdspro.app.util.JsonUtil
import com.wincdspro.app.util.SettingsManager
import java.util.Date

class WincdsClientReports {
    companion object {
        private val client: WincdsClient get() = WincdsClient()

        private fun reportResult(sourceFragment: Fragment, statusCode: Int, result: ReportGenerationResponse?) {
            if (statusCode == SC_OK && result != null) {
                ReportData.add(sourceFragment.requireContext(), result)
                (sourceFragment.activity as ReportActivity).setFragment(fragmentName = OperationNames.RPT_OPERATION_RESULTS, source = sourceFragment)
            } else {
                Toast.makeText(sourceFragment.requireContext(), "$statusCode - ${WincdsClient.lastError}", Toast.LENGTH_LONG).show()
            }
        }

        @JvmStatic
        fun dailyAudit(fragment: Fragment, startDate: Date, endDate: Date, summary: Boolean = false, cashInDrawer: Double = 0.0, priorPeriodCash: Double = 0.0) {
            val args = mapOf(
                "startDate" to formatDateUrl(startDate),
                "endDate" to formatDateUrl(endDate),
                "summary" to JsonUtil.booleanJson(summary),
                "cashInDrawer" to Format.currency(cashInDrawer),
                "priorPeriodCash" to Format.currency(priorPeriodCash),
            )
            client.postReportRequest(SettingsManager.storeNo, REPORT_DAILY_AUDIT, args) { s: Int, r: ReportGenerationResponse? -> reportResult(fragment, s, r) }
        }
    }
}
