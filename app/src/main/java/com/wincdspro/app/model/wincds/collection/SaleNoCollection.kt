package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson

data class SaleNoCollection(var results: List<String> = mutableListOf()) : ToJson()
