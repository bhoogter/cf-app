package com.wincdspro.app.model.wincds.request.tracking

import com.wincdspro.app.mapper.ToJson

data class ResultStatusResponse(
    var result: Boolean? = false,
    var status: String? = "",
) : ToJson()
