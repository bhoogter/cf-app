package com.wincdspro.app.model.wincds.request.newsale

import com.wincdspro.app.mapper.ToJson

class NewSaleRequest(
    var mailIndex: Int? = null,
    var items: List<NewSaleItem> = mutableListOf(),
    var addTax: Boolean = false,

    var addDel: Double? = null,
    var addLab: Double? = null,
    var addStain: Double? = null,

    var delDate: String? = null,
    var pord: String? = null,
) : ToJson()
