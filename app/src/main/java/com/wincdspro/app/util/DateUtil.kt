package com.wincdspro.app.util

import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtil {
    companion object {
        val now: Date get() = Date()
        val today: Date get() = Date()

        fun year(d: Date? = null): Int = formatterDate.format(d ?: now).takeLast(4).toInt()
        fun month(d: Date? = null): Int = formatterDate.format(d ?: now).take(2).toInt()
        fun day(d: Date? = null): Int = formatterDate.format(d ?: now).take(5).takeLast(2).toInt()

        private val parserDate = SimpleDateFormat("MM/dd/yyyy")
        private val parserTime: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        private val parserDateTime: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        private val parserDateUrl = SimpleDateFormat("yyyy-MM-dd")

        private val formatterDate: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        private val formatterTime: SimpleDateFormat = SimpleDateFormat("HH:mm")
        private val formatterDateTime: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")

        private val formatterDateUrl: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        fun parseDate(s: String?): Date? {
            if ((s ?: "").isBlank()) return null
            try {
                return parserDate.parse(s!!)
            } catch (e: Exception) {
            }
            try {
                return parserDateUrl.parse(s ?: "")
            } catch (e: Exception) {
            }
            return null
        }

        fun parseTime(s: String?): Date? = if ((s ?: "").isBlank()) null else parserTime.parse(s!!)
        fun parseDateTime(s: String?): Date? = if ((s ?: "").isBlank()) null else parserDateTime.parse(s!!)

        fun isDate(d: String? = null) = parseDate(d ?: "") != null

        fun formatDate(d: Date = today) = formatterDate.format(d)
        fun formatDate(d: String?) = if (!isDate(d)) "" else formatDate(parseDate(d) ?: today)
        fun formatTime(d: Any = now) = formatterTime.format(d)
        fun formatDateTime(d: Any = now) = formatterDateTime.format(d)

        fun formatDateUrl(d: Any? = null) = formatterDateUrl.format(d ?: today)

        fun dtpValue(dtp: DatePicker) = "${dtp.year.toString().padStart(4, '0')}-${dtp.month.toString().padStart(2, '0')}-${dtp.dayOfMonth.toString().padStart(2, '0')}"

        fun monthStart(s: String) = monthStart(parseDate(s))
        fun monthStart(d: Date? = null) = parseDate("${month(d ?: today)}/1/${year(d ?: today)}")
        fun monthEnd(s: String) = monthEnd(parseDate(s))
        fun monthEnd(d: Date? = null) = Calendar.getInstance().apply { time = d ?: today; set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)); }.time
        fun todayOrBefore(d: Date? = null) = if ((d ?: today).before(today)) d ?: today else today

        fun monthlyReportStart(s: String): Date = parseDate("${month(parseDate(s))}/1/${year(parseDate(s))}") ?: today
        fun monthlyReportStart(d: Date? = null): Date = parseDate("${month(d ?: today)}/1/${year(d)}") ?: today
        fun monthlyReportEnd(s: String): Date = monthlyReportEnd(parseDate(s))
        fun monthlyReportEnd(d: Date? = null): Date = if (month(d ?: today) == month(d ?: today)) monthEnd(d ?: today) else d ?: today
    }
}
