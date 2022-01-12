package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class Audit(
    var auditId: Long? = 0,
    var saleNo: String? = "",
    var name1: String? = "",
    var transDate: String? = "",
    var written: Double? = 0.0,
    var taxCharged: Double? = 0.0,
    var arCashSales: Double? = 0.0,
    var control: Double? = 0.0,
    var undSls: Double? = 0.0,
    var taxRec1: Double? = 0.0,
    var taxCoe: Long? = 0,
    var salesman: String? = "",
    var fldPost: Long? = 0,
    var nonTaxable: Double? = 0.0,
    var cashier: String? = "",
) : ToJson()
