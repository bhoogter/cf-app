package com.wincdspro.app.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.wincdspro.app.model.wincds.core.GrossMargin

@JsonIgnoreProperties(ignoreUnknown = true)
class BosLineModel(
    var marginLine: Int? = null,
    var style: String? = "",
    var vendor: String? = null,
    var vendorNo: String? = null,
    var loc: Int? = null,
    var status: String? = null,
    var quantity: Double? = null,
    var description: String? = null,
    var price: Double? = null
) {
    companion object {
        fun of(i: GrossMargin) = BosLineModel(
            marginLine = i.marginLine,
            style = i.style,
            vendor = i.vendor,
            vendorNo = i.vendorNo,
            loc = i.location,
            status = i.status,
            quantity = i.quantity,
            description = i.desc,
            price = i.sellPrice,
        )
    }
}
