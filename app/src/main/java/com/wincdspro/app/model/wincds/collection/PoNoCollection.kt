package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson

data class PoNoCollection(var results: List<Int> = mutableListOf()) : ToJson()
