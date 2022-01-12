package com.wincdspro.app.model.wincds.request

import com.wincdspro.app.mapper.ToJson

data class PhysicalInventoryRequest(
    var body: String = ""
) : ToJson()
