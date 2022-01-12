package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.util.Mapper
import org.junit.Assert.assertNotNull
import org.junit.Test

class SearchItemCollectionTest {
    @Test
    fun testDeserialize() {
        assertNotNull(Mapper.Companion.fromJson(fullCollection(), SearchItemCollection::class.java))
    }

    private fun fullCollection(): String {
        return """
{
  "results": [
    {
      "rn": 454,
      "style": "6000-066",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "40\" * 60\" CORNER COMPUTER DESK"
    },
    {
      "rn": 456,
      "style": "6000-085",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "6 PC CORNER COMPUTER DESK"
    },
    {
      "rn": 458,
      "style": "6000-091",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "68\" D/O 3PC WALL ENTERTAINMENT CTR."
    },
    {
      "rn": 457,
      "style": "6000-745",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "64\" M/O COMPUTER ROLL TOP DESK"
    },
    {
      "rn": 459,
      "style": "6000-799R",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "78\" OMPUTER DESK & HUTCH"
    },
    {
      "rn": 455,
      "style": "6000-941/942",
      "vendor": "VIRGINIA HOUSE",
      "vendorNo": "800",
      "dept": "3",
      "desc": "GLIDER ROCKER  & OTTOMAN    25-361 BEIGE"
    }
  ]
}        
"""
    }
}
