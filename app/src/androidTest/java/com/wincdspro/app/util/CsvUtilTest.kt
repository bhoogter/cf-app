package com.wincdspro.app.util

import org.junit.Assert.assertEquals
import org.junit.Test

class CsvUtilTest {
    private val testLine1 = "a,b,c,d,e,f"
    val testLine2 = "aa,bb,cc,\"dd,ee\",ff"

    @Test
    fun testCsvField() {
        assertEquals("a", CsvUtil.csvField(testLine1, 0))
        assertEquals("d", CsvUtil.csvField(testLine1, 3))
        assertEquals("e", CsvUtil.csvField(testLine1, 4))
        assertEquals("f", CsvUtil.csvField(testLine1, 5))
        assertEquals("", CsvUtil.csvField(testLine1, 6))

        assertEquals("aa", CsvUtil.csvField(testLine2, 0))
        assertEquals("dd,ee", CsvUtil.csvField(testLine2, 3))
        assertEquals("ff", CsvUtil.csvField(testLine2, 4))
    }

    @Test
    fun testCsvLine() {
        assertEquals("a,\"b\"\"\",c", CsvUtil.csvLine("a", "b\"", "c"))
    }
}
