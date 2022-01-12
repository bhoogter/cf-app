package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class Cash(
    var cashId: Long? = 0,
    var leaseId: String? = "",
    var money: Double? = 0.0,
    var account: String? = "",
    var note: String? = "",
    var transDate: String? = "",
    var cashier: String? = "",
) : ToJson()
