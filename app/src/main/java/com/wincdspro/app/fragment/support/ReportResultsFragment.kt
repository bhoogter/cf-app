package com.wincdspro.app.fragment.support

import android.graphics.Color
import android.graphics.Color.rgb
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.wincdspro.app.R
import com.wincdspro.app.activity.ReportActivity
import com.wincdspro.app.data.ReportData
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import com.wincdspro.app.util.DateUtil
import java.util.Timer
import java.util.TimerTask

class ReportResultsFragment : WincdsFragment() {
    companion object {
        private const val REFRESH_PERIOD = 60_000L
    }

    private val tmr = Timer()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reports_results, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupSwipeMenu()
        loadResultList()
        beginAutoRefresh()
        doRefresh()
    }

    override fun onPause() {
        super.onPause()
        endAutoRefresh()
    }

    override fun onResume() {
        super.onResume()
        beginAutoRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        endAutoRefresh()
    }

    private val lstResults get() = requireActivity().findViewById<SwipeMenuListView>(R.id.report_results_swipe_results)
//    private val refreshLayout get() = requireActivity().findViewById<SwipeRefreshLayout>(R.id.report_results_refresh_results)

    private fun beginAutoRefresh() {
        try {
            tmr.schedule(
                object : TimerTask() {
                    override fun run() = if (doRefresh()) Unit else Unit
                },
                REFRESH_PERIOD, REFRESH_PERIOD
            )
        } catch (e: Exception) {
            // ...  In case they hit back immediately, timer would be cancelled
        }
    }

    private fun endAutoRefresh() = tmr.cancel()

    private fun setupSwipeMenu() {
        val creator = com.baoyz.swipemenulistview.SwipeMenuCreator { menu -> // create "open" item
            val openItem = SwipeMenuItem(requireActivity().applicationContext)
            openItem.background = ColorDrawable(rgb(0xC9, 0xC9, 0xCE)) // set item background
            openItem.width = 160 // set item width (px)
            openItem.title = "Open" // set item title
            openItem.titleSize = 18 // set item title fontsize
            openItem.titleColor = Color.WHITE // set item title font color
            menu.addMenuItem(openItem) // add to menu

            val deleteItem = SwipeMenuItem(requireActivity().applicationContext)
            deleteItem.background = ColorDrawable(rgb(0xF9, 0x3F, 0x25))
            deleteItem.width = 160 // set item width (px)
            deleteItem.title = "Delete" // set item title
            deleteItem.titleSize = 18 // set item title fontsize
            deleteItem.titleColor = Color.WHITE // set item title font color
            menu.addMenuItem(deleteItem) // add to menu
        }

        lstResults.setMenuCreator(creator)

        lstResults.setOnMenuItemClickListener { position, _, index ->
            when (index) {
                0 -> openItem(position)
                1 -> deleteItem(position)
                else -> true
            }
        }

        lstResults.setOnItemLongClickListener { parent, view, position, id ->
            openItem(position)
        }
    }

    private fun openItem(position: Int): Boolean {
        val item = lstResults.adapter.getItem(position).toString()
        val id = resultValue(item)
        if (!ReportData.isReady(requireContext(), id)) {
            toast("Item not ready: ${resultValue(item)}")
            return true
        }

        (requireActivity() as ReportActivity).loadReport(id, this)
        return false
    }

    private fun deleteItem(position: Int): Boolean {
        val item = lstResults.adapter.getItem(position).toString()
        ReportData.remove(requireContext(), resultValue(item))
        return false
    }

    // ready(10) + date(20) + reportName(30) == 61
    private fun resultLine(r: ReportGenerationResponse) = "" + (if (r.ready == true) " READY" else "").padEnd(10) + DateUtil.formatDate(r.date!!).padEnd(20) + r.reportName!!.padEnd(30) + r.resultId
    private fun resultValue(l: String) = l.substring(60).trim()

    private fun loadResultList() {
        val lines = ReportData.results(requireContext())
            .sortedBy(ReportGenerationResponse::date)
            .reversed()
            .map(::resultLine)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.listview_lookup_result, lines)
        requireActivity().runOnUiThread {
            lstResults.adapter = adapter
//            refreshLayout.isRefreshing = false
        }
    }

    private fun doRefresh(): Boolean {
        ReportData.check(requireContext(), ::loadResultList)
        return false
    }
}
