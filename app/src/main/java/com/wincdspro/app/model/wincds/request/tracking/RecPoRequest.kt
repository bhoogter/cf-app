package com.wincdspro.app.model.wincds.request.tracking

import com.wincdspro.app.mapper.ToJson

data class RecPoRequest(
    var poNo: String? = "",
    var style: String? = "",
    var bin: String? = "",
    var serial: String? = "",
) : ToJson()
