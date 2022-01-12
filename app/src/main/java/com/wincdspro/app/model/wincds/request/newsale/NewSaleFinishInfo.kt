package com.wincdspro.app.model.wincds.request.newsale

import com.wincdspro.app.mapper.ToJson

class NewSaleFinishInfo(
    var addTax: Boolean? = false,
    var addDel: Double? = 0.0,
    var addLab: Double? = 0.0,
    var addStain: Double? = 0.0,
    var delDate: String? = null,
    var pord: String? = null,
) : ToJson()
