package com.wincdspro.app.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FileUtilTest {
    @Test
    fun testReadEntireFile_dne() {
        assertEquals("", FileUtil.readEntireFile("does-not-exists.txt"))
    }
}
