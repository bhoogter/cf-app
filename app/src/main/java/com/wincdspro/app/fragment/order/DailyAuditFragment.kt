package com.wincdspro.app.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import com.wincdspro.app.R
import com.wincdspro.app.client.WincdsClientReports
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.util.DateUtil
import com.wincdspro.app.util.DateUtil.Companion.day
import com.wincdspro.app.util.DateUtil.Companion.formatDate
import com.wincdspro.app.util.DateUtil.Companion.month
import com.wincdspro.app.util.DateUtil.Companion.monthlyReportEnd
import com.wincdspro.app.util.DateUtil.Companion.monthlyReportStart
import com.wincdspro.app.util.DateUtil.Companion.parseDate
import com.wincdspro.app.util.DateUtil.Companion.year
import com.wincdspro.app.util.Format
import java.util.Calendar

class DailyAuditFragment : WincdsFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reports_dailyaudit, container, false)
    }

    override fun onStart() {
        super.onStart()
        dtpSelect?.init(year(), month(), day()) { _: DatePicker, y: Int, m: Int, d: Int -> selectedDateChanged(y, m, d) }

        txtDateStart?.setText(formatDate(monthlyReportStart()))
        txtDateEnd?.setText(formatDate(monthlyReportEnd()))

        txtDateStart?.setOnTouchListener { v, event -> getDateStart(); v.performClick() }
        txtDateEnd?.setOnTouchListener { v, event -> getDateEnd(); v.performClick() }
        txtDateStart?.setOnClickListener { getDateStart() }
        txtDateEnd?.setOnClickListener { getDateEnd() }
        txtCashInDrawer?.setOnClickListener { selectedDate() }
        txtPriorPeriodCash?.setOnClickListener { selectedDate() }
        chkDetail?.setOnClickListener { selectedDate() }

        chkSummary?.setOnClickListener { selectedDate() }
        btnSubmit?.setOnClickListener { doSubmit() }
    }

    private fun selectedDateChanged(y: Int, m: Int, d: Int) {
    }

    private val txtDateStart get() = activity?.findViewById<EditText>(R.id.order_dailyaudit_text_start)
    private val txtDateEnd get() = activity?.findViewById<EditText>(R.id.order_dailyaudit_text_end)
    private val dtpSelect get() = activity?.findViewById<DatePicker>(R.id.order_dailyaudit_date_select)
    private val laySelect get() = activity?.findViewById<LinearLayout>(R.id.order_dailyaudit_layout_select)
    private val chkDetail get() = activity?.findViewById<RadioButton>(R.id.order_dailyaudit_option_detail)
    private val chkSummary get() = activity?.findViewById<RadioButton>(R.id.order_dailyaudit_option_summary)
    private val txtCashInDrawer get() = activity?.findViewById<EditText>(R.id.order_dailyaudit_text_cashindrawer)
    private val txtPriorPeriodCash get() = activity?.findViewById<EditText>(R.id.order_dailyaudit_text_priorperiodcash)
    private val btnSubmit get() = activity?.findViewById<Button>(R.id.order_dailyaudit_button_submit)

    private var datePickerVisible: Boolean
        get() = laySelect?.visibility == View.VISIBLE
        set(v) {
            laySelect?.visibility = if (v) View.VISIBLE else View.GONE
        }

    private val selectedDate: Calendar
        get() {
            val cal = Calendar.getInstance()
            cal.set(dtpSelect?.year ?: DateUtil.year(), dtpSelect?.month ?: DateUtil.month(), dtpSelect?.dayOfMonth ?: DateUtil.day())
            return cal
        }

    private var dateStart: String
        get() = (txtDateStart?.text ?: "").toString()
        set(v) {
            txtDateStart?.setText(v)
        }
    private var dateEnd: String
        get() = (txtDateEnd?.text ?: "").toString()
        set(v) {
            txtDateEnd?.setText(v)
        }

    private val selectedDateText: String get() = DateUtil.dtpValue(dtpSelect!!)

    private fun selectedDate(start: String? = null, end: String? = null) {
        datePickerVisible = false
        if (start != null) {
            dateStart = start
            dateEnd = formatDate(monthlyReportEnd(dateStart))
        }
        if (end != null) dateEnd = end
    }

    private fun getDateStart(): Boolean {
        datePickerVisible = true
        dtpSelect?.requestFocus()
        dtpSelect?.init(year(parseDate(dateStart)), month(parseDate(dateStart)) - 1, day(parseDate(dateStart))) { datePicker: DatePicker, y: Int, m: Int, d: Int ->
            selectedDate(start = "${m + 1}/$d/$y")
        }
        dtpSelect?.setOnClickListener { selectedDate(start = selectedDateText) }
        return true
    }

    private fun getDateEnd(): Boolean {
        datePickerVisible = true
        dtpSelect?.requestFocus()
        dtpSelect?.init(year(parseDate(dateEnd)), month(parseDate(dateEnd)) - 1, day(parseDate(dateEnd))) { datePicker: DatePicker, y: Int, m: Int, d: Int ->
            selectedDate(end = "${m + 1}/$d/$y")
        }
        dtpSelect?.setOnClickListener { selectedDate(end = selectedDateText) }
        return true
    }

    private fun valid(issues: MutableList<String> = mutableListOf()): Boolean {
        val pStart = parseDate(dateStart)
        val pEnd = parseDate(dateEnd)
        if (pStart == null) issues.add("Start Date must be a valid date")
        if (pEnd == null) issues.add("End Date must be a valid date")
        if (pStart != null && pEnd != null && pEnd.before(pStart))
            issues.add("End Date must be after Start Date")
        return issues.isEmpty()
    }

    private fun doSubmit() {
        val issues = mutableListOf<String>()
        if (!valid(issues)) {
            toast(issues.joinToString(","))
            return
        }

        WincdsClientReports.dailyAudit(
            this,
            DateUtil.parseDate(dateStart) ?: DateUtil.monthlyReportStart(),
            DateUtil.parseDate(dateEnd) ?: DateUtil.monthlyReportEnd(),
            chkDetail?.isChecked ?: false,
            Format.getPrice(txtCashInDrawer?.text?.toString()),
            Format.getPrice(txtPriorPeriodCash?.text?.toString()),
        )
    }
}
