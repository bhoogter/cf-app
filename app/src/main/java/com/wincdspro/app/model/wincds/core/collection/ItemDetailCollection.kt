package com.wincdspro.app.model.wincds.core.collection

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.core.ItemDetail

data class ItemDetailCollection(var results: List<ItemDetail> = mutableListOf()) : ToJson()
