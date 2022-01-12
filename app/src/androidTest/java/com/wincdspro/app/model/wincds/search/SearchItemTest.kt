package com.wincdspro.app.model.wincds.search

import com.wincdspro.app.util.Mapper
import org.junit.Assert.assertNotNull
import org.junit.Test

class SearchItemTest {
    @Test
    fun testDeserialize() {
        assertNotNull(Mapper.Companion.fromJson(obj(), SearchItem::class.java))
    }

    private fun obj(): String {
        return """
    {
      "rn": 454,
      "style": "6000-066",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "40\" * 60\" CORNER COMPUTER DESK"
}
"""
    }
}
