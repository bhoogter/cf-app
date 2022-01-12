package com.wincdspro.app.model.wincds.request.tracking

import com.wincdspro.app.mapper.ToJson

data class StkLocRequest(
    var style: String? = "",
    var oldBin: String? = "",
    var newBin: String? = "",
    var serial: String? = "",
) : ToJson()
