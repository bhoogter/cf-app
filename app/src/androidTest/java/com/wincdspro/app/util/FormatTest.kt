package com.wincdspro.app.util

import com.wincdspro.app.util.DateUtil.Companion.day
import com.wincdspro.app.util.DateUtil.Companion.month
import com.wincdspro.app.util.DateUtil.Companion.year
import com.wincdspro.app.util.Format.Companion.ani
import com.wincdspro.app.util.Format.Companion.cleanAni
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatTest {
    @Test
    fun testFormatCurrency() {
        assertEquals("$1.00", Format.currency(1.0, true))
        assertEquals("10000.00", Format.sqlCurrency(10000.0))
    }

    @Test
    fun testFormatQuantity() {
        assertEquals("1", Format.quantity(1.0))
        assertEquals("1.1", Format.quantity(1.1))
        assertEquals("1.01", Format.quantity(1.01))
        assertEquals("1.01", Format.quantity(1.011))
    }

    @Test
    fun testFormatGm() {
        assertEquals("1", Format.gm(1.0))
        assertEquals("1.1", Format.gm(1.1))
        assertEquals("1.01", Format.gm(1.01))
        assertEquals("1.01", Format.gm(1.011))
    }

    @Test
    fun testFormatDate() {
        val check = "${month().toString().padStart(2, '0')}/${day().toString().padStart(2, '0')}/${year()}"
        assertEquals(check, Format.date())
        assertEquals("03/03/2003", Format.date("3/3/2003"))
    }

    @Test
    fun testAni() {
        assertEquals("3333333333", cleanAni("333-333-3333"))
        assertEquals("333-333-3333", ani("3333333333"))
        assertEquals("333-333-3333", ani("333333a;alkjdlijsl3333"))
        assertEquals("333-3333", ani("3333333"))
    }
}
