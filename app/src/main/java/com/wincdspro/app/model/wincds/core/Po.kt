package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

class Po(
    var poNo: String = "",
    var vendors: List<String> = mutableListOf(),
    var poDates: List<String> = mutableListOf(),
    var items: List<PoItem> = mutableListOf(),
) : ToJson()
