package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.search.SearchPo

data class SearchPoCollection(var results: List<SearchPo> = mutableListOf()) : ToJson()
