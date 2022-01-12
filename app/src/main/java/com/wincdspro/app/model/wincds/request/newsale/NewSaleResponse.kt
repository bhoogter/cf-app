package com.wincdspro.app.model.wincds.request.newsale

import com.wincdspro.app.mapper.ToJson

data class NewSaleResponse(
    var result: Boolean? = false,
    var status: String? = "",
    var createdDate: String? = "",
    var saleNo: String? = "",
) : ToJson()
