package com.wincdspro.app.util

import com.wincdspro.app.util.DateUtil.Companion.day
import com.wincdspro.app.util.DateUtil.Companion.formatDateUrl
import com.wincdspro.app.util.DateUtil.Companion.isDate
import com.wincdspro.app.util.DateUtil.Companion.month
import com.wincdspro.app.util.DateUtil.Companion.now
import com.wincdspro.app.util.DateUtil.Companion.parseDate
import com.wincdspro.app.util.DateUtil.Companion.today
import com.wincdspro.app.util.DateUtil.Companion.year
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DateUtilTest {

    @Test
    fun testParts() {
        assertEquals(now, today)
        assertEquals(5, month(DateUtil.parseDate("5/07/2021")))
        assertEquals(7, day(DateUtil.parseDate("5/07/2021")))
        assertEquals(2021, year(DateUtil.parseDate("5/07/2021")))
    }

    @Test
    fun testParseDate() {
        assertNotNull(parseDate("5/7/2020"))
        assertNotNull(parseDate("5/7/20"))
        assertNotNull(parseDate("May 7, 2020"))
    }

    @Test
    fun testIsDate() {
        assertTrue(isDate("5/7/2020"))
        assertFalse(isDate("banana"))
    }

    @Test
    fun testFormatDateUrl() {
        assertEquals("2021-05-02", formatDateUrl(parseDate("5/2/2021)")))
    }
}
