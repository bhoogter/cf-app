package com.wincdspro.app.fragment.support

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.ortiz.touchview.TouchImageView
import com.wincdspro.app.R
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.data.ReportData
import com.wincdspro.app.data.StorageBacking
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.util.DateUtil
import com.wincdspro.app.util.SettingsManager
import java.io.File
import java.io.InputStream

class ReportViewFragment : WincdsFragment() {
    private var resultId: String? = null
    private var renderer: PdfRenderer? = null
    private var mPage: Int = 1

    private var page: Int
        get() = mPage
        set(v) {
            setPdfPage(v)
        }

    private val pages: Int = renderer?.pageCount ?: 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        resultId = arguments?.getString("resultId")
        return inflater.inflate(R.layout.fragment_reports_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadReport()
        btnFirst.setOnClickListener { page = 1 }
        btnLeft.setOnClickListener { page-- }
        btnRight.setOnClickListener { page++ }
        btnLast.setOnClickListener { page = renderer?.pageCount ?: 1 }
    }

    private val lblReportType get() = requireActivity().findViewById<TextView>(R.id.report_view_text_reporttype)
    private val lblReportDate get() = requireActivity().findViewById<TextView>(R.id.report_view_text_reportdate)
    private val lblReportPage get() = requireActivity().findViewById<TextView>(R.id.report_view_text_reportpage)
    private val btnFirst get() = requireActivity().findViewById<Button>(R.id.report_view_button_first)
    private val btnLeft get() = requireActivity().findViewById<Button>(R.id.report_view_button_left)
    private val btnRight get() = requireActivity().findViewById<Button>(R.id.report_view_button_right)
    private val btnLast get() = requireActivity().findViewById<Button>(R.id.report_view_button_last)
    private val prgSpinner get() = requireActivity().findViewById<ProgressBar>(R.id.report_view_prg_spinner)
    private val imgReport get() = requireActivity().findViewById<TouchImageView>(R.id.report_view_image_report)

    fun isLoading() = prgSpinner.visibility == View.VISIBLE
    private fun setLoading(loading: Boolean = false, gone: Boolean = false) {
        prgSpinner.visibility = if (loading && !gone) View.VISIBLE else View.GONE
        imgReport.visibility = if (!loading && !gone) View.VISIBLE else View.GONE
    }

    private fun loadReport() {
        setLoading(true)
        val info = ReportData.get(requireContext(), resultId!!)
        lblReportDate.text = DateUtil.formatDateTime(info?.date!!)
        lblReportType.text = info.reportName
        lblReportPage.text = ""
        client.getReportResult(SettingsManager.storeNo, resultId!!, ::reportResult)
    }

    private fun reportResult(status: Int, info: InputStream?) {
        if (status == SC_OK && info != null) {
            setLoading(false)
            val file = storeFile(info)
            renderer = PdfRenderer(ParcelFileDescriptor.open(file, MODE_READ_ONLY))

            page = 1
        } else {
            setLoading(gone = true)
            toast("Failed loading report [$status]: ${WincdsClient.lastError}")
        }
    }

    private fun storeFile(info: InputStream): File {
        val file = StorageBacking.cacheFile(requireContext(), "$resultId.pdf", "reports")
        if (!file.exists()) file.createNewFile()
        file.writeBytes(info.readBytes())
        return file
    }

    private fun getPageBitmap(pg: Int): Bitmap {
        val pdfPage = renderer!!.openPage(page - 1) // 0 based
        val mBitmap = Bitmap.createBitmap(850, 1100, Bitmap.Config.ARGB_8888)
        pdfPage.render(mBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        pdfPage.close()
        return mBitmap
    }

    private fun setPdfPage(v: Int) {
        if (renderer == null) return
        var iv = v
        val pageCount = renderer?.pageCount!!
        if (iv < 1) iv = 1
        if (iv > pageCount) iv = pageCount
        mPage = iv
        imgReport.setImageBitmap(getPageBitmap(page))
        imgReport.resetZoom()
        val text = "$page of $pageCount"
        lblReportPage.text = text
    }
}
