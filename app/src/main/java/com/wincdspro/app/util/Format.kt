package com.wincdspro.app.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Date

class Format {
    companion object {
        fun getPrice(v: String?): Double = (v ?: "").replace("$", "").toDoubleOrNull() ?: 0.0
        fun currency(v: Double, dollarSign: Boolean = false, commas: Boolean = true): String = NumberFormat
            .getCurrencyInstance().format(v)
            .replace(if (dollarSign) "$$$" else "$", "")
            .replace(if (commas) ",,," else ",", "")

        fun sqlCurrency(v: Double) = currency(v, dollarSign = false, commas = false)

        fun quantity(v: Double): String = if (v == 0.0) "" else DecimalFormat("0.##").format(v)
        fun gm(v: Double): String = quantity(v)

        fun cleanAni(s: String?): String = (s ?: "").trim().filter { it.isDigit() }.removePrefix("1")
        fun ani(s: String?): String = when {
            cleanAni(s).length == 7 -> "${cleanAni(s).substring(0, 3)}-${cleanAni(s).substring(3)}"
            cleanAni(s).length >= 10 -> "${cleanAni(s).substring(0, 3)}-${cleanAni(s).substring(3, 6)}-${cleanAni(s).substring(6)}"
            else -> cleanAni(s)
        }

        fun date(d: Date? = null): String = DateUtil.formatDate(d ?: DateUtil.today)
        fun date(d: String?): String = DateUtil.formatDate(d)
        fun dateUrl(d: Date? = null): String = DateUtil.formatDateUrl(d ?: DateUtil.today)
        fun dateUrl(d: String?): String = DateUtil.formatDateUrl(d)
    }
}
