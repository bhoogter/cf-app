package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class Sale(
    var leaseNo: String? = "",
    var index: Long? = 0,
    var sale: Double = 0.0,
    var deposit: Double = 0.0,
    var status: String? = "",
    var comm: String? = null,
    var margStart: Long? = null,
    var lastPay: String? = null,
    var nonTaxable: Double = 0.0,
    var salesman: String? = "",
    var fldPosted: Long? = null,
    var arNo: String? = null,

    var items: List<GrossMargin>? = null,
    var mail: Mail? = null,
) : ToJson()
