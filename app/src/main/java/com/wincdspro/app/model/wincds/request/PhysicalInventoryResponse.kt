package com.wincdspro.app.model.wincds.request

import com.wincdspro.app.mapper.ToJson

data class PhysicalInventoryResponse(
    var response: String = ""
) : ToJson()
