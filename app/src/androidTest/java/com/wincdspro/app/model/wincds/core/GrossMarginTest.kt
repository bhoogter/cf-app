package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.util.Mapper
import org.junit.Assert
import org.junit.Test

class GrossMarginTest {

    @Test
    fun testDeserialize() {
        val value = fullGm()
        val obj = Mapper.Companion.fromJson(value, GrossMargin::class.java)
        Assert.assertNotNull(obj)
    }

    private fun fullGm() = """
{
  "marginLine": 2090,
  "saleNo": "10348",
  "quantity": 1,
  "style": "6000-066",
  "vendor": "VIRGINIA HOUSE",
  "desc": "TRIPLE DRESSER",
  "porD": "",
  "deptNo": "3",
  "vendorNo": "800",
  "commission": " ",
  "cost": 450,
  "itemFreight": 36,
  "sellPrice": 935.5,
  "salesman": "01",
  "status": "ST",
  "location": 1,
  "sellDate": "8/27/1999",
  "delDate": null,
  "rn": 454,
  "store": 1,
  "name": "CARTER",
  "shipDate": null,
  "gM": 48,
  "tele": "7402863125",
  "detail": 469,
  "mailIndex": 302,
  "sS": "",
  "delPrint": "",
  "pullPrint": null,
  "commPd": null,
  "fldPosted": null,
  "spiff": null,
  "salesSplit": null,
  "stopStart": null,
  "stopEnd": null,
  "isPackage": 0,
  "packSell": 935.5,
  "packSellGM": 48,
  "packSaleGM": 48.05,
  "transID": null
}
"""
}
