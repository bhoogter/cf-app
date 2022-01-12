package com.wincdspro.app.data

import android.content.Context
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import com.wincdspro.app.util.SettingsManager
import java.util.stream.Collectors.toList

class ReportData {
    companion object {
        private const val ID = "ReportData"

        private val client = WincdsClient()
        private var data: ReportRequests? = null

        private fun load(context: Context) = StorageBacking.load(ID, context, ReportRequests::class.java, ReportRequests())
        private fun save(context: Context, setData: ReportRequests? = null) = StorageBacking.save(ID, context, setData ?: data, ReportRequests())
        private fun init(context: Context): ReportRequests {
            data = StorageBacking.init(ID, context, ReportRequests::class.java, data, ReportRequests())
            if (data == null) data = load(context)
            if (data == null) data = save(context)
            return data!!
        }

        fun results(context: Context) = init(context).requests.toList() // immutable
        fun clear(context: Context) = save(context, ReportRequests())
        fun add(context: Context, r: ReportGenerationResponse) = save(context, init(context).apply { requests.add(r) })
        fun remove(context: Context, id: String) = save(context, init(context).apply { requests.removeAll(requests.filter { it.resultId == id }) })
        fun markReady(context: Context, id: String) = save(context, init(context).apply { requests.firstOrNull { it.resultId == id }?.ready = true })
        fun get(context: Context, id: String) = init(context).requests.firstOrNull { it.resultId == id }
        fun isReady(context: Context, id: String): Boolean = get(context, id)?.ready ?: false

        fun check(context: Context, checkComplete: (() -> Unit)? = null) {
            results(context)
                .filter { !(it.ready ?: false) }
                .parallelStream()
                .map {
                    client.headReportResultReady(SettingsManager.storeNo, it.resultId!!) { status ->
                        if (status == SC_OK) markReady(context, it.resultId!!)
                    }
                }
                .collect(toList())
            checkComplete?.invoke()
        }
    }

    data class ReportRequests(
        var requests: MutableList<ReportGenerationResponse> = mutableListOf()
    )
}
