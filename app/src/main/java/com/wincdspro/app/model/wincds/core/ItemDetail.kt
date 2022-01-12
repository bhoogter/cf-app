package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.exception.InvalidStoreException
import com.wincdspro.app.mapper.ToJson

data class ItemDetail(
    var detailID: Int = 0,
    var style: String = "",
    var saleNo: String = "",
    var name: String = "",
    var misc: String = "",
    var ddate1: String? = "",
    var trans: String? = "",
    var amtSold: Double = 0.0,
    var newStock: Double = 0.0,
    var specOrd: Double = 0.0,
    var lAW: Double = 0.0,
    var loc1: Double = 0.0,
    var loc2: Double = 0.0,
    var loc3: Double = 0.0,
    var loc4: Double = 0.0,
    var loc5: Double = 0.0,
    var loc6: Double = 0.0,
    var loc7: Double = 0.0,
    var loc8: Double = 0.0,
    var loc9: Double = 0.0,
    var loc10: Double = 0.0,
    var loc11: Double = 0.0,
    var loc12: Double = 0.0,
    var loc13: Double = 0.0,
    var loc14: Double = 0.0,
    var loc15: Double = 0.0,
    var loc16: Double = 0.0,
    var store: Int = 0,
    var invRn: Int = 0,
    var marginRn: Int = 0,
    var itemCost: Double = 0.0,
    var notes: String? = "",
    var loc17: Double = 0.0,
    var loc18: Double = 0.0,
    var loc19: Double = 0.0,
    var loc20: Double = 0.0,
    var loc21: Double = 0.0,
    var loc22: Double = 0.0,
    var loc23: Double = 0.0,
    var loc24: Double = 0.0,
    var loc25: Double = 0.0,
    var loc26: Double = 0.0,
    var loc27: Double = 0.0,
    var loc28: Double = 0.0,
    var loc29: Double = 0.0,
    var loc30: Double = 0.0,
    var loc31: Double = 0.0,
    var loc32: Double = 0.0,
) : ToJson() {
    companion object {
        private const val MIN_FIELD = 1
        private const val MAX_FIELD = 32

        fun varidateField(n: Int): Int {
            if (n >= MIN_FIELD || n <= MAX_FIELD) return n
            throw InvalidStoreException("Invalid Store: $n")
        }
    }

    private fun locBalField(n: Int) = javaClass.getDeclaredField("loc$n")
    fun getLocBal(n: Int): Double = (locBalField(varidateField(n)).get(this) ?: 0.0) as Double
    fun setLocBal(n: Int, v: Double): Unit = locBalField(varidateField(n)).set(this, v)

    private fun onOrderField(n: Int) = javaClass.getDeclaredField("onOrder$n")
    fun getOnOrder(n: Int) = (onOrderField(varidateField(n)).get(this) ?: 0.0) as Double
    fun setOnOrder(n: Int, v: Double) = onOrderField(varidateField(n)).set(this, v)
}
