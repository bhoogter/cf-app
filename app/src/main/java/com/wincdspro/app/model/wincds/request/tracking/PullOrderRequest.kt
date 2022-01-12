package com.wincdspro.app.model.wincds.request.tracking

import com.wincdspro.app.mapper.ToJson

data class PullOrderRequest(
    var saleNo: String? = "",
    var style: String? = "",
    var bin: String? = "",
    var serial: String? = "",
    var storeNo: String? = "",
) : ToJson()
