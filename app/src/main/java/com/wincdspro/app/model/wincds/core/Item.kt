package com.wincdspro.app.model.wincds.core

import com.fasterxml.jackson.annotation.JsonProperty
import com.wincdspro.app.exception.InvalidStoreException
import com.wincdspro.app.mapper.ToJson

data class Item(
    var style: String = "",
    var vendor: String = "",
    var rn: Int = 0,
    var rDate: String = "",
    var dept: String = "",
    var vendorNo: String = "",
    var desc: String = "",
    var minStk: Double = 0.0,
    var freight: Double = 0.0,
    var freightType: Int = 0,
    @get:JsonProperty("gM")
    @set:JsonProperty("gM")
    var gM: Double = 0.0,
    var markUp: Double = 0.0,
    var cost: Double = 0.0,
    var landed: Double = 0.0,
    var onSale: Double = 0.0,
    var list: Double = 0.0,
    var comments: String? = null,
    var available: Double = 0.0,
    var onHand: Double = 0.0,

    var sales1: Double = 0.0,
    var sales2: Double = 0.0,
    var sales3: Double = 0.0,
    var sales4: Double = 0.0,
    var poSold: Double = 0.0,

    var pSales1: Double = 0.0,
    var pSales2: Double = 0.0,
    var pSales3: Double = 0.0,
    var pSales4: Double = 0.0,

    var gMROI: Double? = null,
    var fabric: String? = null,
    var sku: String? = null,
    var blank: String? = null,

    var spiff: Double? = null,
    var cubes: Double? = null,

    var loc1Bal: Double = 0.0,
    var loc2Bal: Double = 0.0,
    var loc3Bal: Double = 0.0,
    var loc4Bal: Double = 0.0,
    var loc5Bal: Double = 0.0,
    var loc6Bal: Double = 0.0,
    var loc7Bal: Double = 0.0,
    var loc8Bal: Double = 0.0,
    var loc9Bal: Double = 0.0,
    var loc10Bal: Double = 0.0,
    var loc11Bal: Double = 0.0,
    var loc12Bal: Double = 0.0,
    var loc13Bal: Double = 0.0,
    var loc14Bal: Double = 0.0,
    var loc15Bal: Double = 0.0,
    var loc16Bal: Double = 0.0,
    var loc17Bal: Double = 0.0,
    var loc18Bal: Double = 0.0,
    var loc19Bal: Double = 0.0,
    var loc20Bal: Double = 0.0,
    var loc21Bal: Double = 0.0,
    var loc22Bal: Double = 0.0,
    var loc23Bal: Double = 0.0,
    var loc24Bal: Double = 0.0,
    var loc25Bal: Double = 0.0,
    var loc26Bal: Double = 0.0,
    var loc27Bal: Double = 0.0,
    var loc28Bal: Double = 0.0,
    var loc29Bal: Double = 0.0,
    var loc30Bal: Double = 0.0,
    var loc31Bal: Double = 0.0,
    var loc32Bal: Double = 0.0,

    var onOrder1: Double = 0.0,
    var onOrder2: Double = 0.0,
    var onOrder3: Double = 0.0,
    var onOrder4: Double = 0.0,
    var onOrder5: Double = 0.0,
    var onOrder6: Double = 0.0,
    var onOrder7: Double = 0.0,
    var onOrder8: Double = 0.0,
    var onOrder9: Double = 0.0,
    var onOrder10: Double = 0.0,
    var onOrder11: Double = 0.0,
    var onOrder12: Double = 0.0,
    var onOrder13: Double = 0.0,
    var onOrder14: Double = 0.0,
    var onOrder15: Double = 0.0,
    var onOrder16: Double = 0.0,
    var onOrder17: Double = 0.0,
    var onOrder18: Double = 0.0,
    var onOrder19: Double = 0.0,
    var onOrder20: Double = 0.0,
    var onOrder21: Double = 0.0,
    var onOrder22: Double = 0.0,
    var onOrder23: Double = 0.0,
    var onOrder24: Double = 0.0,
    var onOrder25: Double = 0.0,
    var onOrder26: Double = 0.0,
    var onOrder27: Double = 0.0,
    var onOrder28: Double = 0.0,
    var onOrder29: Double = 0.0,
    var onOrder30: Double = 0.0,
    var onOrder31: Double = 0.0,
    var onOrder32: Double = 0.0,
    var distributors: String? = "",
) : ToJson() {
    companion object {
        private const val MIN_FIELD = 1
        private const val MAX_FIELD = 32

        fun varidateField(n: Int): Int {
            if (n >= MIN_FIELD || n <= MAX_FIELD) return n
            throw InvalidStoreException("Invalid Store: $n")
        }
    }

    private fun locBalField(n: Int) = javaClass.getDeclaredField("loc${n}Bal")
    fun getLocBal(n: Int): Double = (locBalField(varidateField(n)).get(this) ?: 0.0) as Double
    fun setLocBal(n: Int, v: Double): Unit = locBalField(varidateField(n)).set(this, v)

    private fun onOrderField(n: Int) = javaClass.getDeclaredField("onOrder$n")
    fun getOnOrder(n: Int) = (onOrderField(varidateField(n)).get(this) ?: 0.0) as Double
    fun setOnOrder(n: Int, v: Double) = onOrderField(varidateField(n)).set(this, v)
}
