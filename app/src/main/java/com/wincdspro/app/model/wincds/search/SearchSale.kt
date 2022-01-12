package com.wincdspro.app.model.wincds.search

import com.wincdspro.app.mapper.ToJson

data class SearchSale(
    val leaseNo: String = "",
    val index: Int = 0,
    val last: String? = null,
    val first: String? = null,
    val tele: String? = null,
    val city: String? = null,
    val status: String? = null,
    val sale: Double = 0.0,
    val deposit: Double = 0.0,
    val lastPay: String? = null,
) : ToJson()
